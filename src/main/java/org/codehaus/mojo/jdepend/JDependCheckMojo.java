package org.codehaus.mojo.jdepend;

/*
 * #%L
 * JDepend Maven Plugin
 * %%
 * Copyright (C) 2006 - 2014 Codehaus
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import jdepend.framework.JDepend;
import jdepend.framework.JavaClass;
import jdepend.framework.JavaPackage;
import jdepend.framework.ParserListener;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Checks the compiled project classes for package dependency cycles and fails the build when a cycle is found.
 */
@Mojo(name = "check", defaultPhase = LifecyclePhase.VERIFY, threadSafe = true)
public class JDependCheckMojo extends AbstractMojo implements ParserListener {
    /**
     * Directory containing the class files to check.
     */
    @Parameter(defaultValue = "${project.build.outputDirectory}", property = "jdepend.classDirectory", required = true)
    private File classDirectory;

    /**
     * Skip execution of the plugin.
     */
    @Parameter(defaultValue = "false", property = "jdepend.skip")
    private boolean skip;

    private int parsedClassCount;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping JDepend cycle check on behalf of user");
            return;
        }

        if (classDirectory == null) {
            throw new MojoExecutionException("The class directory is not configured");
        }

        if (!classDirectory.exists()) {
            getLog().info("Skipping JDepend cycle check because the class directory does not exist: " + classDirectory);
            return;
        }

        if (!classDirectory.isDirectory()) {
            throw new MojoExecutionException("The configured class directory is not a directory: " + classDirectory);
        }

        JDepend analyzer = new JDepend();
        Collection<?> packages;
        try {
            int classFileCount = countClassFiles(classDirectory);
            parsedClassCount = 0;
            analyzer.analyzeInnerClasses(true);
            analyzer.addParseListener(this);
            analyzer.addDirectory(classDirectory.getAbsolutePath());
            packages = analyzer.analyze();
            if (parsedClassCount != classFileCount) {
                throw new MojoExecutionException("JDepend parsed " + parsedClassCount + " of " + classFileCount
                        + " class files in " + classDirectory
                        + "; the remaining class files may be unreadable or use unsupported bytecode");
            }
        } catch (IOException | RuntimeException e) {
            throw new MojoExecutionException("Failed to analyze classes in " + classDirectory, e);
        }

        List<String> cycles = findCycles(packages);
        if (!cycles.isEmpty()) {
            throw new MojoFailureException(formatFailureMessage(cycles));
        }

        getLog().info("No package dependency cycles found.");
    }

    @Override
    public void onParsedJavaClass(JavaClass javaClass) {
        parsedClassCount++;
    }

    static List<String> findCycles(Collection<?> packages) {
        Set<String> cycles = new TreeSet<>();

        sortEfferents(packages);

        for (Object candidate : packages) {
            JavaPackage javaPackage = (JavaPackage) candidate;
            List<JavaPackage> path = new ArrayList<>();
            if (javaPackage.collectCycle(path)) {
                String cycle = canonicalizeCycle(path);
                if (cycle != null) {
                    cycles.add(cycle);
                }
            }
        }

        return new ArrayList<>(cycles);
    }

    private static void sortEfferents(Collection<?> packages) {
        for (Object candidate : packages) {
            JavaPackage javaPackage = (JavaPackage) candidate;
            Map<String, JavaPackage> efferentsByName = new TreeMap<>();
            for (Object efferent : javaPackage.getEfferents()) {
                JavaPackage efferentPackage = (JavaPackage) efferent;
                efferentsByName.put(efferentPackage.getName(), efferentPackage);
            }
            javaPackage.setEfferents(efferentsByName.values());
        }
    }

    private static int countClassFiles(File directory) throws IOException {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Unable to list " + directory);
        }

        int count = 0;
        for (File file : files) {
            if (file.isDirectory()) {
                count += countClassFiles(file);
            } else if (file.isFile()) {
                if (hasExtension(file.getName(), ".class")) {
                    count++;
                } else if (isArchive(file.getName())) {
                    count += countArchiveClassFiles(file);
                }
            }
        }
        return count;
    }

    private static int countArchiveClassFiles(File file) throws IOException {
        int count = 0;
        try (JarFile archive = new JarFile(file)) {
            for (java.util.Enumeration<JarEntry> entries = archive.entries(); entries.hasMoreElements(); ) {
                if (hasExtension(entries.nextElement().getName(), ".class")) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isArchive(String fileName) {
        return hasExtension(fileName, ".jar") || hasExtension(fileName, ".war") || hasExtension(fileName, ".zip");
    }

    private static boolean hasExtension(String fileName, String extension) {
        int offset = fileName.length() - extension.length();
        return offset >= 0 && fileName.regionMatches(true, offset, extension, 0, extension.length());
    }

    private static String canonicalizeCycle(List<JavaPackage> path) {
        if (path.size() < 2) {
            return null;
        }

        String repeatedPackage = path.get(path.size() - 1).getName();
        int cycleStart = -1;
        for (int index = 0; index < path.size() - 1; index++) {
            if (repeatedPackage.equals(path.get(index).getName())) {
                cycleStart = index;
                break;
            }
        }

        if (cycleStart < 0) {
            return null;
        }

        List<String> packageNames = new ArrayList<>();
        for (int index = cycleStart; index < path.size() - 1; index++) {
            packageNames.add(path.get(index).getName());
        }

        int canonicalStart = 0;
        for (int index = 1; index < packageNames.size(); index++) {
            if (packageNames.get(index).compareTo(packageNames.get(canonicalStart)) < 0) {
                canonicalStart = index;
            }
        }

        StringBuilder cycle = new StringBuilder();
        for (int index = 0; index < packageNames.size(); index++) {
            if (cycle.length() > 0) {
                cycle.append(" -> ");
            }
            cycle.append(packageNames.get((canonicalStart + index) % packageNames.size()));
        }
        cycle.append(" -> ").append(packageNames.get(canonicalStart));
        return cycle.toString();
    }

    private static String formatFailureMessage(List<String> cycles) {
        StringBuilder message =
                new StringBuilder("JDepend check failed: package dependency cycles detected, including:");
        for (String cycle : cycles) {
            message.append(System.lineSeparator()).append("  - ").append(cycle);
        }
        message.append(System.lineSeparator()).append("Run jdepend:generate to inspect the full dependency report.");
        return message.toString();
    }
}

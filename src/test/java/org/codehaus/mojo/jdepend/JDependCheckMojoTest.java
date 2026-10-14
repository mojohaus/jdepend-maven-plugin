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
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import jdepend.framework.JavaPackage;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JDependCheckMojoTest {
    @Test
    void returnsNoCyclesForOneWayDependencies() {
        JavaPackage api = new JavaPackage("org.example.api");
        JavaPackage implementation = new JavaPackage("org.example.implementation");
        implementation.dependsUpon(api);

        assertEquals(Collections.emptyList(), JDependCheckMojo.findCycles(Arrays.asList(api, implementation)));
    }

    @Test
    void returnsCanonicalCycleWithoutLeadingPackages() {
        JavaPackage alpha = new JavaPackage("org.example.alpha");
        JavaPackage beta = new JavaPackage("org.example.beta");
        JavaPackage entry = new JavaPackage("org.example.entry");

        alpha.dependsUpon(beta);
        beta.dependsUpon(alpha);
        entry.dependsUpon(beta);

        assertEquals(
                Collections.singletonList("org.example.alpha -> org.example.beta -> org.example.alpha"),
                JDependCheckMojo.findCycles(Arrays.asList(entry, beta, alpha)));
    }

    @Test
    void choosesCycleDeterministicallyWhenSeveralAreAvailable() {
        JavaPackage alpha = new JavaPackage("org.example.alpha");
        JavaPackage beta = new JavaPackage("org.example.beta");
        JavaPackage gamma = new JavaPackage("org.example.gamma");

        alpha.dependsUpon(gamma);
        alpha.dependsUpon(beta);
        beta.dependsUpon(alpha);
        gamma.dependsUpon(alpha);

        assertEquals(
                Collections.singletonList("org.example.alpha -> org.example.beta -> org.example.alpha"),
                JDependCheckMojo.findCycles(Arrays.asList(gamma, beta, alpha)));
    }

    @Test
    void failsWhenJDependCannotParseEveryClass(@TempDir File classDirectory) throws Exception {
        Files.write(new File(classDirectory, "Broken.class").toPath(), new byte[] {0, 1, 2, 3});

        JDependCheckMojo mojo = mojoFor(classDirectory);

        MojoExecutionException exception = assertThrows(MojoExecutionException.class, mojo::execute);
        assertTrue(exception.getMessage().contains("JDepend parsed 0 of 1 class files"));
    }

    @Test
    void acceptsClassFilesInsideArchives(@TempDir File classDirectory) throws Exception {
        File archive = new File(classDirectory, "classes.jar");
        try (InputStream input = JDependCheckMojo.class.getResourceAsStream("JDependCheckMojo.class");
                JarOutputStream output = new JarOutputStream(Files.newOutputStream(archive.toPath()))) {
            output.putNextEntry(new JarEntry("org/codehaus/mojo/jdepend/JDependCheckMojo.class"));
            byte[] buffer = new byte[4096];
            for (int read = input.read(buffer); read >= 0; read = input.read(buffer)) {
                output.write(buffer, 0, read);
            }
        }

        mojoFor(classDirectory).execute();
    }

    private static JDependCheckMojo mojoFor(File classDirectory) throws ReflectiveOperationException {
        JDependCheckMojo mojo = new JDependCheckMojo();
        Field classDirectoryField = JDependCheckMojo.class.getDeclaredField("classDirectory");
        classDirectoryField.setAccessible(true);
        classDirectoryField.set(mojo, classDirectory);
        return mojo;
    }
}

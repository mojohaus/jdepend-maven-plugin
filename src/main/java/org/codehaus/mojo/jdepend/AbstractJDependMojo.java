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
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import jdepend.framework.PackageFilter;
import jdepend.xmlui.JDepend;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;

/**
 * @author Karl-Heinz Marbaise
 */
public abstract class AbstractJDependMojo extends AbstractMavenReport {
    JDependXMLReportParser xmlParser;

    /**
     * Directory where the generated output site files will be located.
     */
    @Parameter(defaultValue = "${project.build.directory}/site", property = "jdepend.outputDirectory", required = true)
    protected String outputDirectory;

    /**
     * Directory of the project.
     */
    @Parameter(defaultValue = "${basedir}", property = "jdepend.projectDirectory")
    private String projectDirectory;

    /**
     * Directory containing the class files.
     */
    @Parameter(defaultValue = "${project.build.outputDirectory}", property = "jdepend.classDirectory", required = true)
    private String classDirectory;

    /**
     * Location of the generated JDepend xml report.
     */
    @Parameter(defaultValue = "${project.build.directory}/jdepend-report.xml", required = true, readonly = true)
    private String reportFile;

    /**
     * Skip execution of the plugin.
     */
    @Parameter(defaultValue = "false", property = "jdepend.skip")
    private boolean skip;

    /**
     * Package name prefixes to exclude from the JDepend analysis.
     */
    @Parameter
    private List<String> ignorePackages;

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.AbstractMavenReport#executeReport(java.util.Locale)
     */
    public void executeReport(Locale locale) throws MavenReportException {
        if (skip) {
            getLog().info("Skipping execution on behalf of user");
            return;
        }

        try {
            File outputDirFile = new File(outputDirectory);

            if (!outputDirFile.exists()) {
                boolean success = outputDirFile.mkdirs();
                if (!success) {
                    throw new MavenReportException("Could not create directory " + outputDirectory);
                }
            }

            generateJDependXmlReport();

            xmlParser = new JDependXMLReportParser(new File(getReportFile()));

            generateReport(locale);
        } catch (Exception e) {
            throw new MavenReportException("Failed to execute JDepend", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.AbstractMavenReport#canGenerateReport()
     */
    public boolean canGenerateReport() {
        return new File(classDirectory).exists();
    }

    private void generateJDependXmlReport() throws IOException {
        try (PrintWriter writer =
                new PrintWriter(Files.newBufferedWriter(Paths.get(getReportFile()), StandardCharsets.UTF_8))) {
            JDepend jdepend = new JDepend(writer);
            jdepend.setFilter(createPackageFilter());
            jdepend.addDirectory(getClassDirectory());
            jdepend.analyze();
        }
    }

    private PackageFilter createPackageFilter() {
        PackageFilter packageFilter = new PackageFilter();

        if (ignorePackages != null) {
            for (String ignorePackage : ignorePackages) {
                if (ignorePackage != null) {
                    packageFilter.addPackage(ignorePackage.trim());
                }
            }
        }

        return packageFilter;
    }

    public void generateReport(Locale locale) throws MavenReportException {
        Sink sink;
        ReportGenerator report = new ReportGenerator();
        try {
            sink = getSink();

            report.doGenerateReport(getBundle(locale), sink, xmlParser);
        } catch (Exception e) {
            throw new MavenReportException("Failed to generate JDepend report", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.MavenReport#getDescription(java.util.Locale)
     */
    public String getDescription(Locale locale) {
        return getBundle(locale).getString("report.jdepend.description");
    }

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.MavenReport#getName(java.util.Locale)
     */
    public String getName(Locale locale) {
        return getBundle(locale).getString("report.jdepend.name");
    }

    private ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle(
                "org.codehaus.mojo.jdepend.jdepend-report",
                locale,
                this.getClass().getClassLoader());
    }

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.MavenReport#getOutputName()
     */
    public String getOutputName() {
        return "jdepend-report";
    }

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.AbstractMavenReport#getOutputDirectory()
     */
    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    /**
     * @return
     */
    public String getReportFile() {
        return reportFile;
    }

    public void setReportFile(String reportFile) {
        this.reportFile = reportFile;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.AbstractMavenReport#getSiteRenderer()
     */
    public Renderer getSiteRenderer() {
        return siteRenderer;
    }

    public void setSiteRenderer(Renderer siteRenderer) {
        this.siteRenderer = siteRenderer;
    }

    public String getProjectDirectory() {
        return projectDirectory;
    }

    public void setProjectDirectory(String projectDirectory) {
        this.projectDirectory = projectDirectory;
    }

    public String getClassDirectory() {
        return classDirectory;
    }

    public void setClassDirectory(String classDirectory) {
        this.classDirectory = classDirectory;
    }
}

package com.mergere;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;

import org.codehaus.doxia.sink.Sink;
import org.codehaus.doxia.site.renderer.SiteRenderer;


/**
 * Maven JDepend report generator.
 *
 * @goal generate
 * 
 */
public class JDependMojo extends AbstractMavenReport
{
    /**
     * @parameter expression="${project}"
     * @readonly
     */
    private MavenProject project;
    
    /**
     * @parameter expression="${project.build.directory}/site"
     * @readonly
     */
    private String outputDirectory;
    
    /**
     *@parameter expression="${basedir}"
     *
     */
    private String projectDirectory;
    
    /**
     *The -file argument
     *
     *@parameter expression="-file"
     *@readonly
     */
    private String argument;
    
    /**
     *File name of the generated xml report.
     *
     *@parameter expression="target/jdepend-report.xml"
     *@readonly
     */
    private String reportFile;
    
    /**
     * @parameter expression="${component.org.codehaus.doxia.site.renderer.SiteRenderer}"
     * @readonly
     */
    private SiteRenderer siteRenderer;
    
    JDependXMLReportParser xmlParser;
    
    public void executeReport(Locale locale) throws MavenReportException
    {
        JDependMain jdepend = new JDependMain();
        
        try
        {
            File outputDirFile = new File(outputDirectory);
            
            if(!outputDirFile.exists())
            {
                boolean success = outputDirFile.mkdirs();
                if(!success)
                {
                    throw new MavenReportException("Could not create directory " 
                            + outputDirectory);
                }
            }
            
            String[] args = new String[3];
            
            args[0] = getProjectDirectory();
            
            args[1] = getArgument();
            
            args[2] = getReportFile();
            
            jdepend.execute(args);
            
            xmlParser = new JDependXMLReportParser(getProjectDirectory() + "/" + getReportFile());
            
            generateReport(locale);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void generateReport(Locale locale) throws MavenReportException
    {
        Sink sink;
        ReportGenerator report = new ReportGenerator();
        try
        {
            sink = getSink();
            
            report.doGenerateReport(getBundle(locale), sink, xmlParser);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String getDescription(Locale locale) 
    {
        return getBundle(locale).getString("report.jdepend.description");
    }
    
    public String getName(Locale locale) 
    {
        return getBundle(locale).getString("report.jdepend.name");
    }
    
    private ResourceBundle getBundle(Locale locale) 
    {
        return ResourceBundle.getBundle("jdepend-report", locale, 
                this.getClass().getClassLoader());
    }

    public String getOutputName() 
    {
        return "jdepend-report";
    }
    
    public MavenProject getProject() 
    {
        return project;
    }

    public void setProject(MavenProject project) 
    {
        this.project = project;
    }

    public String getOutputDirectory() 
    {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) 
    {
        this.outputDirectory = outputDirectory;
    }

    public String getArgument() 
    {
        return argument;
    }

    public void setArgument(String argument) 
    {
        this.argument = argument;
    }

    public String getReportFile() 
    {
        return reportFile;
    }

    public void setReportFile(String reportFile) 
    {
        this.reportFile = reportFile;
    }

    public SiteRenderer getSiteRenderer() 
    {
        return siteRenderer;
    }

    public void setSiteRenderer(SiteRenderer siteRenderer) 
    {
        this.siteRenderer = siteRenderer;
    }

    public String getProjectDirectory() {
        return projectDirectory;
    }

    public void setProjectDirectory(String projectDirectory) {
        this.projectDirectory = projectDirectory;
    }
}

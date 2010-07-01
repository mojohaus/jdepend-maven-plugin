package org.codehaus.mojo.jdepend;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import jdepend.xmlui.JDepend;

import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Run JDepend and generate a site report.
 *
 * @author aramirez@exist.com
 * @version $Id$
 * @goal generate
 * @execute phase="compile"
 * @description Goal which generate the JDepend metrics.
 */
public class JDependMojo extends AbstractMavenReport
{
    /**
     * @parameter expression="${project}"
     * @readonly
     */
    private MavenProject project;

    /**
     * Directory where the generated output site files will be located.
     *
     * @parameter expression="${project.build.directory}/site"
     * @required
     */
    private String outputDirectory;

    /**
     * Directory of the project.
     *
     * @parameter expression="${basedir}"
     */
    private String projectDirectory;

    /**
     * Directory containing the class files.
     *
     * @parameter expression="${project.build.outputDirectory}"
     * @required
     */
    private String classDirectory;

    /**
     * @parameter default-value="-file"
     * @readonly
     */
    private String argument;

    /**
     * Location of the generated JDepend xml report.
     *
     * @parameter default-value="${project.build.directory}/jdepend-report.xml"
     * @required
     * @readonly
     */
    private String reportFile;

    /**
     * Doxia Site Renderer
     *
     * @component
     */
    private Renderer siteRenderer;

    JDependXMLReportParser xmlParser;

    /**
     * Execute the generate of reports.
     */
    public void executeReport( Locale locale )
        throws MavenReportException
    {
        try
        {
            File outputDirFile = new File( outputDirectory );

            if ( !outputDirFile.exists() )
            {
                boolean success = outputDirFile.mkdirs();
                if ( !success )
                {
                    throw new MavenReportException( "Could not create directory " + outputDirectory );
                }
            }

            JDepend.main( getArgumentList( getArgument(), getReportFile(), getClassDirectory() ) );

            xmlParser = new JDependXMLReportParser( new File( getReportFile() ) );

            generateReport( locale );
        }
        catch ( Exception e )
        {
            throw new MavenReportException( "Failed to execute JDepend", e );
        }
    }

    /**
     * Cf. overriden method documentation.
     *
     * @see org.apache.maven.reporting.MavenReport#canGenerateReport()
     */
    public boolean canGenerateReport()
    {
        File srcDir = new File( classDirectory );
        if ( !srcDir.exists() )
        {
            return false;
        }
        return true;
    }

    /**
     * Sets and get the arguments passed for the JDepend.
     *
     * @param argument   Accepts parameter with "-file" string.
     * @param reportFile Accepts the location of the generated JDepend xml report file.
     * @param classDir   Accepts the location of the classes.
     * @return String[]  Returns the array to be pass as parameters for JDepend.
     */
    private String[] getArgumentList( String argument, String reportFile, String classDir )
    {
        ArrayList argList = new ArrayList();

        argList.add( argument );

        argList.add( reportFile );

        argList.add( classDir );

        return (String[]) argList.toArray( new String[argList.size()] );
    }

    public void generateReport( Locale locale )
        throws MavenReportException
    {
        Sink sink;
        ReportGenerator report = new ReportGenerator();
        try
        {
            sink = getSink();

            report.doGenerateReport( getBundle( locale ), sink, xmlParser );
        }
        catch ( Exception e )
        {
            throw new MavenReportException( "Failed to generate JDepend report", e );
        }
    }

    public String getDescription( Locale locale )
    {
        return getBundle( locale ).getString( "report.jdepend.description" );
    }

    public String getName( Locale locale )
    {
        return getBundle( locale ).getString( "report.jdepend.name" );
    }

    private ResourceBundle getBundle( Locale locale )
    {
        return ResourceBundle.getBundle( "org.codehaus.mojo.jdepend.jdepend-report", locale,
                                         this.getClass().getClassLoader() );
    }

    public String getOutputName()
    {
        return "jdepend-report";
    }

    public MavenProject getProject()
    {
        return project;
    }

    public void setProject( MavenProject project )
    {
        this.project = project;
    }

    public String getOutputDirectory()
    {
        return outputDirectory;
    }

    public void setOutputDirectory( String outputDirectory )
    {
        this.outputDirectory = outputDirectory;
    }

    public String getArgument()
    {
        return argument;
    }

    public void setArgument( String argument )
    {
        this.argument = argument;
    }

    public String getReportFile()
    {
        return reportFile;
    }

    public void setReportFile( String reportFile )
    {
        this.reportFile = reportFile;
    }

    public Renderer getSiteRenderer()
    {
        return siteRenderer;
    }

    public void setSiteRenderer( Renderer siteRenderer )
    {
        this.siteRenderer = siteRenderer;
    }

    public String getProjectDirectory()
    {
        return projectDirectory;
    }

    public void setProjectDirectory( String projectDirectory )
    {
        this.projectDirectory = projectDirectory;
    }

    public String getClassDirectory()
    {
        return classDirectory;
    }

    public void setClassDirectory( String classDirectory )
    {
        this.classDirectory = classDirectory;
    }
}

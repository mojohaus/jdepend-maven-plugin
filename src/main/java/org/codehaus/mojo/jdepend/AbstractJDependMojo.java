package org.codehaus.mojo.jdepend;

/*
 * #%L
 * JDepend Maven Plugin
 * %%
 * Copyright (C) 2006 - 2014 Codehaus
 * %%
 * Licensed under the Apache License 2.0
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.  
 * The ASF licenses this file to you under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file 
 * except in compliance with the License.  
 * 
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * #L%
 */

import jdepend.xmlui.JDepend;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Karl-Heinz Marbaise
 */
public abstract class AbstractJDependMojo
    extends AbstractMavenReport
{
    JDependXMLReportParser xmlParser;

    @Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;

    /**
     * Directory where the generated output site files will be located.
     */
    @Parameter( defaultValue = "${project.build.directory}/site", property = "jdepend.outputDirectory", required = true )
    private String outputDirectory;

    /**
     * Directory of the project.
     */
    @Parameter( defaultValue = "${basedir}", property = "jdepend.projectDirectory" )
    private String projectDirectory;

    /**
     * Directory containing the class files.
     */
    @Parameter( defaultValue = "${project.build.outputDirectory}", property = "jdepend.classDirectory", required = true )
    private String classDirectory;

    /**
     * Location of the generated JDepend xml report.
     */
    @Parameter( defaultValue = "${project.build.directory}/jdepend-report.xml", required = true, readonly = true )
    private String reportFile;

    /**
     * Skip execution of the plugin.
     */
    @Parameter( defaultValue = "false", property = "jdepend.skip" )
    private boolean skip;

    /**
     * Doxia Site Renderer
     */
    @Component
    private Renderer siteRenderer;

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.AbstractMavenReport#executeReport(java.util.Locale)
     */
    public void executeReport( Locale locale )
        throws MavenReportException
    {
        if ( skip )
        {
            getLog().info( "Skipping execution on behalf of user" );
            return;
        }

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

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.AbstractMavenReport#canGenerateReport()
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
     * @param argument Accepts parameter with "-file" string.
     * @param locationXMLreportFile Accepts the location of the generated JDepend xml report file.
     * @param classDir Accepts the location of the classes.
     * @return String[] Returns the array to be pass as parameters for JDepend.
     */
    private String[] getArgumentList( String argument, String locationXMLreportFile, String classDir )
    {
        List<String> argList = new ArrayList<String>();

        argList.add( argument );

        argList.add( locationXMLreportFile );

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

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.MavenReport#getDescription(java.util.Locale)
     */
    public String getDescription( Locale locale )
    {
        return getBundle( locale ).getString( "report.jdepend.description" );
    }

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.MavenReport#getName(java.util.Locale)
     */
    public String getName( Locale locale )
    {
        return getBundle( locale ).getString( "report.jdepend.name" );
    }

    private ResourceBundle getBundle( Locale locale )
    {
        return ResourceBundle.getBundle( "org.codehaus.mojo.jdepend.jdepend-report", locale,
                                         this.getClass().getClassLoader() );
    }

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.MavenReport#getOutputName()
     */
    public String getOutputName()
    {
        return "jdepend-report";
    }

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.AbstractMavenReport#getProject()
     */
    public MavenProject getProject()
    {
        return project;
    }

    /**
     * @param project
     */
    public void setProject( MavenProject project )
    {
        this.project = project;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.AbstractMavenReport#getOutputDirectory()
     */
    public String getOutputDirectory()
    {
        return outputDirectory;
    }

    public void setOutputDirectory( String outputDirectory )
    {
        this.outputDirectory = outputDirectory;
    }

    /**
     * @return The argument.
     */
    public String getArgument()
    {
        return "-file";
    }

    /**
     * @return
     */
    public String getReportFile()
    {
        return reportFile;
    }

    public void setReportFile( String reportFile )
    {
        this.reportFile = reportFile;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.maven.reporting.AbstractMavenReport#getSiteRenderer()
     */
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

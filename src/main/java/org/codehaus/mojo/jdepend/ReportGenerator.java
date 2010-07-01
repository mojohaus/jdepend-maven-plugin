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

import org.apache.maven.doxia.sink.Sink;
import org.codehaus.mojo.jdepend.objects.CyclePackage;
import org.codehaus.mojo.jdepend.objects.JDPackage;
import org.codehaus.mojo.jdepend.objects.Stats;

import java.util.List;
import java.util.ResourceBundle;

public class ReportGenerator
{
    private JDependXMLReportParser jdepend;

    private JDPackage jdpackage;

    private Stats stats;

    private List list;

    private CyclePackage cyclepackage;

    /**
     * Creates a new instance of ReportGenerator.
     */
    public ReportGenerator()
    {
    }

    public void doGenerateReport( ResourceBundle bundle, Sink sink, JDependXMLReportParser jdepend )
    {
        this.jdepend = jdepend;

        sink.head();
        sink.title();
        sink.text( bundle.getString( "report.title" ) ); //$NON-NLS-1$
        sink.title_();
        sink.head_();

        sink.body();
        sink.section1();

        sink.sectionTitle1();
        sink.text( bundle.getString( "report.metricresults" ) ); //$NON-NLS-1$
        sink.sectionTitle1_();

        doSectionLinks( bundle, sink );

        sink.lineBreak();
        sink.lineBreak();

        sink.text( bundle.getString( "report.intro" ) ); //$NON-NLS-1$

        sink.lineBreak();
        sink.lineBreak();

        doSummarySection( bundle, sink );

        doPackagesSection( bundle, sink );

        doCycleSection( bundle, sink );

        doExplanationSection( bundle, sink );

        sink.section1_();
        sink.body_();
        sink.flush();
        sink.close();

    }

    public void doSectionLinks( ResourceBundle bundle, Sink sink )
    {

        sink.text( bundle.getString( "report.square-open" ) ); //$NON-NLS-1$
        sink.link( bundle.getString( "report.summary.anchor" ) ); //$NON-NLS-1$
        sink.text( bundle.getString( "report.summary.text" ) ); //$NON-NLS-1$
        sink.link_();
        sink.text( bundle.getString( "report.square-close" ) ); //$NON-NLS-1$

        sink.text( bundle.getString( "report.square-open" ) ); //$NON-NLS-1$
        sink.link( bundle.getString( "report.packages.anchor" ) ); //$NON-NLS-1$
        sink.text( bundle.getString( "report.packages.text" ) ); //$NON-NLS-1$
        sink.link_();
        sink.text( bundle.getString( "report.square-close" ) ); //$NON-NLS-1$

        sink.text( bundle.getString( "report.square-open" ) ); //$NON-NLS-1$
        sink.link( bundle.getString( "report.cycles.anchor" ) ); //$NON-NLS-1$
        sink.text( bundle.getString( "report.cycles.text" ) ); //$NON-NLS-1$
        sink.link_();
        sink.text( bundle.getString( "report.square-close" ) ); //$NON-NLS-1$

        sink.text( bundle.getString( "report.square-open" ) ); //$NON-NLS-1$
        sink.link( bundle.getString( "report.explanations.anchor" ) ); //$NON-NLS-1$
        sink.text( bundle.getString( "report.explanations.text" ) ); //$NON-NLS-1$
        sink.link_();
        sink.text( bundle.getString( "report.square-close" ) ); //$NON-NLS-1$

    }

    public void doSummarySection( ResourceBundle bundle, Sink sink )
    {

        sink.anchor( bundle.getString( "report.summary.text" ) ); //$NON-NLS-1$
        sink.anchor_();

        sink.sectionTitle1();
        sink.text( bundle.getString( "report.summary.title" ) ); //$NON-NLS-1$
        sink.sectionTitle1_();

        doSectionLinks( bundle, sink );

        sink.lineBreak();
        sink.lineBreak();

        sink.table();
        sink.tableRow();

        sink.tableHeaderCell();
        sink.text( bundle.getString( "report.package.title" ) ); //$NON-NLS-1$
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( bundle.getString( "report.TC" ) ); //$NON-NLS-1$
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( bundle.getString( "report.CC" ) ); //$NON-NLS-1$
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( bundle.getString( "report.AC" ) ); //$NON-NLS-1$
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( bundle.getString( "report.Ca" ) ); //$NON-NLS-1$
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( bundle.getString( "report.Ce" ) ); //$NON-NLS-1$
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( bundle.getString( "report.A" ) ); //$NON-NLS-1$
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( bundle.getString( "report.I" ) ); //$NON-NLS-1$
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( bundle.getString( "report.D" ) ); //$NON-NLS-1$
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( bundle.getString( "report.V" ) ); //$NON-NLS-1$
        sink.tableHeaderCell_();

        sink.tableRow_();

        list = jdepend.packages;

        for ( int i = 0; i < list.size(); i++ )
        {
            jdpackage = (JDPackage) list.get( i );

            stats = jdpackage.getStats();

            sink.tableRow();
            sink.tableCell();
            sink.link( bundle.getString( "report.dash" ) + jdpackage.getPackageName() ); //$NON-NLS-1$
            sink.text( jdpackage.getPackageName() );
            sink.link_();
            sink.tableCell_();

            sink.tableCell();
            sink.text( stats.getTotalClasses() );
            sink.tableCell_();

            sink.tableCell();
            sink.text( stats.getConcreteClasses() );
            sink.tableCell_();

            sink.tableCell();
            sink.text( stats.getAbstractClasses() );
            sink.tableCell_();

            sink.tableCell();
            sink.text( stats.getCa() );
            sink.tableCell_();

            sink.tableCell();
            sink.text( stats.getCe() );
            sink.tableCell_();

            sink.tableCell();
            sink.text( convertToPercent( stats.getA() ) );
            sink.tableCell_();

            sink.tableCell();
            sink.text( convertToPercent( stats.getI() ) );
            sink.tableCell_();

            sink.tableCell();
            sink.text( convertToPercent( stats.getD() ) );
            sink.tableCell_();

            sink.tableCell();
            sink.text( stats.getV() );
            sink.tableCell_();

            sink.tableRow_();
        }

        sink.table_();

    }

    public void doPackagesSection( ResourceBundle bundle, Sink sink )
    {

        sink.anchor( bundle.getString( "report.packages.text" ) ); //$NON-NLS-1$
        sink.anchor_();

        sink.sectionTitle1();
        sink.text( bundle.getString( "report.packages.title" ) ); //$NON-NLS-1$
        sink.sectionTitle1_();

        doSectionLinks( bundle, sink );

        sink.lineBreak();

        doPackage( bundle, sink );

    }

    public void doPackage( ResourceBundle bundle, Sink sink )
    {
        list = jdepend.packages;

        if ( list.size() <= 0 )
        {
            sink.text( bundle.getString( "report.nopackages" ) ); //$NON-NLS-1$
            sink.lineBreak();
        }
        else
        {
            for ( int i = 0; i < list.size(); i++ )
            {
                jdpackage = (JDPackage) list.get( i );

                stats = jdpackage.getStats();

                sink.anchor( jdpackage.getPackageName() );
                sink.anchor_();

                sink.sectionTitle2();
                sink.text( jdpackage.getPackageName() );
                sink.sectionTitle2_();

                sink.table();

                /* Headers */

                sink.tableRow();

                sink.tableHeaderCell();
                sink.text( bundle.getString( "report.afferentcouplings.title" ) ); //$NON-NLS-1$
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( bundle.getString( "report.efferentcouplings.title" ) ); //$NON-NLS-1$
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( bundle.getString( "report.abstractness.title" ) ); //$NON-NLS-1$
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( bundle.getString( "report.instability.title" ) ); //$NON-NLS-1$
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( bundle.getString( "report.distance.title" ) ); //$NON-NLS-1$
                sink.tableHeaderCell_();

                sink.tableRow_();

                /* Values */

                sink.tableRow();

                sink.tableCell();
                sink.text( stats.getCa() );
                sink.tableCell_();

                sink.tableCell();
                sink.text( stats.getCe() );
                sink.tableCell_();

                sink.tableCell();
                sink.text( convertToPercent( stats.getA() ) );
                sink.tableCell_();

                sink.tableCell();
                sink.text( convertToPercent( stats.getI() ) );
                sink.tableCell_();

                sink.tableCell();
                sink.text( convertToPercent( stats.getD() ) );
                sink.tableCell_();

                sink.tableRow_();

                sink.table_();

                /* New Table */

                sink.table();
                sink.tableRow();

                /*
                 * Headers 
                 */

                sink.tableHeaderCell();
                sink.text( bundle.getString( "report.abstractclasses.title" ) ); //$NON-NLS-1$
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( bundle.getString( "report.concreteclasses.title" ) ); //$NON-NLS-1$
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( bundle.getString( "report.usedbypackages" ) ); //$NON-NLS-1$
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( bundle.getString( "report.usespackage" ) ); //$NON-NLS-1$
                sink.tableHeaderCell_();

                sink.tableRow_();

                /*
                 * Values
                 */

                sink.tableRow();

                /* Abstract Classes */
                List abstractList = jdpackage.getAbstractClasses();

                sink.tableCell();

                if ( abstractList.size() <= 0 )
                {
                    sink.italic();
                    sink.text( bundle.getString( "report.none" ) ); //$NON-NLS-1$
                    sink.italic_();
                }
                else
                {
                    for ( int j = 0; j < abstractList.size(); j++ )
                    {
                        sink.text( (String) abstractList.get( j ) );
                        sink.lineBreak();
                    }
                }
                sink.tableCell_();

                /* Concrete Classes */
                java.util.List concreteList = jdpackage.getConcreteClasses();

                sink.tableCell();

                if ( concreteList.size() <= 0 )
                {
                    sink.italic();
                    sink.text( bundle.getString( "report.none" ) ); //$NON-NLS-1$
                    sink.italic_();
                }
                else
                {
                    for ( int j = 0; j < concreteList.size(); j++ )
                    {
                        sink.text( (String) concreteList.get( j ) );
                        sink.lineBreak();
                    }
                }
                sink.tableCell_();

                /* Used By Packages */
                List aList = jdpackage.getUsedBy();

                sink.tableCell();

                if ( aList.size() <= 0 )
                {
                    sink.italic();
                    sink.text( bundle.getString( "report.none" ) ); //$NON-NLS-1$
                    sink.italic_();
                }
                else
                {
                    for ( int j = 0; j < aList.size(); j++ )
                    {
                        sink.text( (String) aList.get( j ) );
                        sink.lineBreak();
                    }
                }
                sink.tableCell_();

                aList = null;

                /* Uses Package */
                aList = jdpackage.getDependsUpon();

                sink.tableCell();

                if ( aList.size() <= 0 )
                {
                    sink.italic();
                    sink.text( bundle.getString( "report.none" ) ); //$NON-NLS-1$
                    sink.italic_();
                }
                else
                {
                    for ( int j = 0; j < aList.size(); j++ )
                    {
                        sink.text( (String) aList.get( j ) );
                        sink.lineBreak();
                    }
                }
                sink.tableCell_();

                sink.tableRow_();

                sink.table_();
            }
        }

    }

    public void doCycleSection( ResourceBundle bundle, Sink sink )
    {

        sink.anchor( bundle.getString( "report.cycles.text" ) ); //$NON-NLS-1$
        sink.anchor_();

        sink.sectionTitle1();
        sink.text( bundle.getString( "report.cycles.title" ) ); //$NON-NLS-1$
        sink.sectionTitle1_();

        doSectionLinks( bundle, sink );

        sink.lineBreak();
        sink.lineBreak();

        doCycles( bundle, sink );

    }

    public void doCycles( ResourceBundle bundle, Sink sink )
    {

        List cycleList = jdepend.cycles;

        if ( cycleList.size() <= 0 )
        {
            sink.text( bundle.getString( "report.nocyclicdependencies" ) ); //$NON-NLS-1$
            sink.lineBreak();
        }
        else
        {
            sink.table();

            sink.tableRow();
            sink.tableHeaderCell();
            sink.text( bundle.getString( "report.package.title" ) ); //$NON-NLS-1$
            sink.tableHeaderCell_();

            sink.tableHeaderCell();
            sink.text( bundle.getString( "report.packagedependencies" ) ); //$NON-NLS-1$
            sink.tableHeaderCell_();
            sink.tableRow_();

            for ( int i = 0; i < cycleList.size(); i++ )
            {
                cyclepackage = (CyclePackage) cycleList.get( i );

                sink.tableRow();
                sink.tableCell();
                sink.text( cyclepackage.getName() );
                sink.tableCell_();

                /* Package Dependencies List */

                List packageList = cyclepackage.getPackageList();
                sink.tableCell();
                if ( packageList.size() <= 0 )
                {
                    sink.italic();
                    sink.text( bundle.getString( "report.none" ) ); //$NON-NLS-1$
                    sink.italic_();
                }
                else
                {
                    for ( int j = 0; j < packageList.size(); j++ )
                    {
                        sink.text( (String) packageList.get( j ) );
                        sink.lineBreak();
                    }
                }
                sink.tableCell_();
                sink.tableRow_();

            }
            sink.table_();
        }

    }

    public void doExplanationSection( ResourceBundle bundle, Sink sink )
    {

        sink.anchor( bundle.getString( "report.explanations" ) ); //$NON-NLS-1$
        sink.anchor_();

        sink.sectionTitle1();
        sink.text( bundle.getString( "report.explanation.title" ) ); //$NON-NLS-1$
        sink.sectionTitle1_();

        doSectionLinks( bundle, sink );

        sink.lineBreak();
        sink.lineBreak();

        sink.text( bundle.getString( "report.explanation.description" ) ); //$NON-NLS-1$

        sink.lineBreak();
        sink.lineBreak();

        sink.table();

        sink.tableRow();
        sink.tableHeaderCell();
        sink.text( bundle.getString( "report.term" ) ); //$NON-NLS-1$
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( bundle.getString( "report.description" ) ); //$NON-NLS-1$
        sink.tableHeaderCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( bundle.getString( "report.numberofclasses.title" ) ); //$NON-NLS-1$
        sink.tableCell_();

        sink.tableCell();
        sink.text( bundle.getString( "report.numberofclasses.description" ) ); //$NON-NLS-1$
        sink.tableCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( bundle.getString( "report.afferentcouplings.title" ) ); //$NON-NLS-1$
        sink.tableCell_();

        sink.tableCell();
        sink.text( bundle.getString( "report.afferentcouplings.description" ) ); //$NON-NLS-1$
        sink.tableCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( bundle.getString( "report.efferentcouplings.title" ) ); //$NON-NLS-1$
        sink.tableCell_();

        sink.tableCell();
        sink.text( bundle.getString( "report.efferentcouplings.description" ) ); //$NON-NLS-1$
        sink.tableCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( bundle.getString( "report.abstractness.title" ) ); //$NON-NLS-1$
        sink.tableCell_();

        sink.tableCell();
        sink.text( bundle.getString( "report.abstractness.description" ) ); //$NON-NLS-1$
        sink.tableCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( bundle.getString( "report.instability.title" ) ); //$NON-NLS-1$
        sink.tableCell_();

        sink.tableCell();
        sink.text( bundle.getString( "report.instability.description" ) ); //$NON-NLS-1$
        sink.tableCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( bundle.getString( "report.distance.title" ) ); //$NON-NLS-1$
        sink.tableCell_();

        sink.tableCell();
        sink.text( bundle.getString( "report.distance.description" ) ); //$NON-NLS-1$
        sink.tableCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( bundle.getString( "report.cycles.title" ) ); //$NON-NLS-1$
        sink.tableCell_();

        sink.tableCell();
        sink.text( bundle.getString( "report.cycles.description" ) ); //$NON-NLS-1$
        sink.tableCell_();
        sink.tableRow_();

        sink.table_();

    }

    private String convertToPercent( String value )
    {
        float ival = 0;

        ival = Float.parseFloat( value );
        ival = ival * 100;
        value = String.valueOf( ival ) + "%"; //$NON-NLS-1$

        return value;
    }
}

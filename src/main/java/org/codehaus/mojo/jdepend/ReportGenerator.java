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

package org.codehaus.mojo.jdepend;

import java.util.List;
import java.util.ResourceBundle;

import org.codehaus.doxia.sink.Sink;
import org.codehaus.mojo.jdepend.objects.CyclePackage;
import org.codehaus.mojo.jdepend.objects.JDPackage;
import org.codehaus.mojo.jdepend.objects.Stats;

public class ReportGenerator
{
    private JDependXMLReportParser jdepend;

    private JDPackage jdpackage;

    private Stats stats;

    private List list;

    private CyclePackage cyclepackage;

    /** Creates a new instance of ReportGenerator */
    public ReportGenerator()
    {
    }

    public void doGenerateReport( ResourceBundle bundle, Sink sink, JDependXMLReportParser jdepend )
    {
        this.jdepend = jdepend;

        sink.head();
        sink.title();
        sink.text( "JDepend Report Metrics" );
        sink.title_();
        sink.head_();

        sink.body();
        sink.section1();

        sink.sectionTitle1();
        sink.text( "Metric Results" );
        sink.sectionTitle1_();

        doSectionLinks( bundle, sink );

        sink.lineBreak();
        sink.lineBreak();

        sink.text( "The following document contains the results of a JDepend metric analysis. "
            + "The various metrics are defined at the bottom of this document." );

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

        sink.text( "[ " );
        sink.link( "#summary" );
        sink.text( "summary" );
        sink.link_();
        sink.text( " ] " );

        sink.text( "[ " );
        sink.link( "#packages" );
        sink.text( "packages" );
        sink.link_();
        sink.text( " ] " );

        sink.text( "[ " );
        sink.link( "#cycles" );
        sink.text( "cycles" );
        sink.link_();
        sink.text( " ] " );

        sink.text( "[ " );
        sink.link( "#explanations" );
        sink.text( "explanations" );
        sink.link_();
        sink.text( " ] " );

    }

    public void doSummarySection( ResourceBundle bundle, Sink sink )
    {

        sink.anchor( "summary" );
        sink.anchor_();

        sink.sectionTitle1();
        sink.text( "Summary" );
        sink.sectionTitle1_();

        doSectionLinks( bundle, sink );

        sink.lineBreak();
        sink.lineBreak();

        sink.table();
        sink.tableRow();

        sink.tableHeaderCell();
        sink.text( "Package" );
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( "TC" );
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( "CC" );
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( "AC" );
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( "Ca" );
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( "Ce" );
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( "A" );
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( "I" );
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( "D" );
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( "V" );
        sink.tableHeaderCell_();

        sink.tableRow_();

        list = jdepend.packages;

        for ( int i = 0; i < list.size(); i++ )
        {
            jdpackage = (JDPackage) list.get( i );

            stats = jdpackage.getStats();

            sink.tableRow();
            sink.tableCell();
            sink.link( "#" + jdpackage.getPackageName() );
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

        sink.anchor( "packages" );
        sink.anchor_();

        sink.sectionTitle1();
        sink.text( "Packages" );
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
            sink.text( "There are no package used." );
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
                sink.text( "Afferent Couplings" );
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( "Efferent Couplings" );
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( "Abstractness" );
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( "Instability" );
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( "Distance" );
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
                sink.text( "Abstract Classes" );
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( "Concrete Classes" );
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( "Used by Packages" );
                sink.tableHeaderCell_();

                sink.tableHeaderCell();
                sink.text( "Uses Packages" );
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
                    sink.text( "None" );
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
                    sink.text( "None" );
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
                    sink.text( "None" );
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
                    sink.text( "None" );
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

        sink.anchor( "cycles" );
        sink.anchor_();

        sink.sectionTitle1();
        sink.text( "Cycles" );
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
            sink.text( "There are no cyclic dependencies." );
            sink.lineBreak();
        }
        else
        {
            sink.table();

            sink.tableRow();
            sink.tableHeaderCell();
            sink.text( "Package" );
            sink.tableHeaderCell_();

            sink.tableHeaderCell();
            sink.text( "Package Dependencies" );
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
                    sink.text( "None" );
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

        sink.anchor( "explanations" );
        sink.anchor_();

        sink.sectionTitle1();
        sink.text( "Explanation" );
        sink.sectionTitle1_();

        doSectionLinks( bundle, sink );

        sink.lineBreak();
        sink.lineBreak();

        sink.text( "The following explanations are for quick reference and"
            + " are lifted directly from the original JDepend documentation." );

        sink.lineBreak();
        sink.lineBreak();

        sink.table();

        sink.tableRow();
        sink.tableHeaderCell();
        sink.text( "Term" );
        sink.tableHeaderCell_();

        sink.tableHeaderCell();
        sink.text( "Description" );
        sink.tableHeaderCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( "Number of Classes" );
        sink.tableCell_();

        sink.tableCell();
        sink.text( "The number of concrete and abstract classes (and interfaces) "
            + "in the package is an indicator of the extensibility of the package." );
        sink.tableCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( "Afferent Couplings" );
        sink.tableCell_();

        sink.tableCell();
        sink.text( "The number of other packages that depend upon classes within "
            + "the package is an indicator of the package's responsibility." );
        sink.tableCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( "Efferent Couplings" );
        sink.tableCell_();

        sink.tableCell();
        sink.text( "The number of other packages that the classes in the package "
            + "depend upon is an indicator of the package's independence." );
        sink.tableCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( "Abstractness" );
        sink.tableCell_();

        sink.tableCell();
        sink.text( "The ratio of the number of abstract classes (and interfaces) "
            + "in the analyzed package to the total number of classes in the "
            + "analyzed package. The range for this metric is 0 to 1, with A=0 "
            + "indicating a completely concrete package and A=1 indicating a " + "completely abstract package." );
        sink.tableCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( "Instability" );
        sink.tableCell_();

        sink.tableCell();
        sink.text( "The ratio of efferent coupling (Ce) to total coupling "
            + "(Ce / (Ce + Ca)). This metric is an indicator of the package's "
            + "resilience to change. The range for this metric is 0 to 1, with "
            + "I=0 indicating a completely stable package and I=1 indicating a " + "completely instable package." );
        sink.tableCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( "Distance" );
        sink.tableCell_();

        sink.tableCell();
        sink.text( "The perpendicular distance of a package from the idealized "
            + "line A + I = 1. This metric is an indicator of the package's "
            + "balance between abstractness and stability. A package squarely "
            + "on the main sequence is optimally balanced with respect to its "
            + "abstractness and stability. Ideal packages are either completely "
            + "abstract and stable (x=0, y=1) or completely concrete and instable "
            + "(x=1, y=0). The range for this metric is 0 to 1, with D=0 indicating "
            + "a package that is coincident with the main sequence and D=1 indicating "
            + "a package that is as far from the main sequence as possible." );
        sink.tableCell_();
        sink.tableRow_();

        sink.tableRow();
        sink.tableCell();
        sink.text( "Cycles" );
        sink.tableCell_();

        sink.tableCell();
        sink.text( "Packages participating in a package dependency cycle are "
            + "in a deadly embrace with respect to reusability and their "
            + "release cycle. Package dependency cycles can be easily identified "
            + "by reviewing the textual reports of dependency cycles. Once these "
            + "dependency cycles have been identified with JDepend, they can be "
            + "broken by employing various object-oriented techniques." );
        sink.tableCell_();
        sink.tableRow_();

        sink.table_();

    }

    private String convertToPercent( String value )
    {
        float ival = 0;

        ival = Float.parseFloat( value );
        ival = ival * 100;
        value = String.valueOf( ival ) + "%";

        return value;
    }
}

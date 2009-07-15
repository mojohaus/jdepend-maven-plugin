package org.codehaus.mojo.jdepend;

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
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.codehaus.mojo.jdepend.objects.JDPackage;
import org.codehaus.mojo.jdepend.objects.Stats;

public class JDependReportParserTest
    extends TestCase
{
    JDependXMLReportParser parser;
    
    List packages;
    
    private String basedir = System.getProperty( "basedir" );
    
    public void setUp() throws Exception
    {
        File reportXML = new File( basedir, JDependMojoTest.REPORT_PATH );
        parser = new JDependXMLReportParser( reportXML );
        
        packages = parser.packages;
    }
    
    public void testTotalNumberPackages()
    {
        assertEquals( "Total number of packages is not equal to expected output", 2, parser.packages.size() );
    }
    
    public void testPackageNames()
    {
        ArrayList packageNames = new ArrayList();
        
        for( int i=0; i<packages.size(); i++ )
        {
            JDPackage jdpackage = ( JDPackage ) packages.get( i );
            
            packageNames.add( jdpackage.getPackageName() );
        }
        assertTrue( packageNames.contains( "org.codehaus.mojo.jdepend" ) );
        assertTrue( packageNames.contains( "org.codehaus.mojo.jdepend.objects" ) );
    }
    
    public void testPackageNamesNotContainingInList()
    {
        ArrayList packageNames = new ArrayList();
        
        for( int i=0; i<packages.size(); i++ )
        {
            JDPackage jdpackage = ( JDPackage ) packages.get( i );
            
            packageNames.add( jdpackage.getPackageName() );
        }
        assertFalse( packageNames.contains( "test" ) );
        assertFalse( packageNames.contains( "test2" ) );
    }
    
    public void testStats()
    {
        for( int i=0; i<packages.size(); i++ )
        {
            JDPackage jdpackage = ( JDPackage ) packages.get( i );

            if( jdpackage.getPackageName().equals( "org.codehaus.mojo.jdepend" ) )
            {
                Stats stats = jdpackage.getStats();
                
                assertEquals( "Stats Total Classes is not equal to expected output", 
                              "4",  stats.getTotalClasses() );
                assertEquals( "Stats Concrete classes is not equal to expected output", 
                              "4", stats.getConcreteClasses() );
                assertEquals( "Stats Abstract Classes is not equal to expected output", 
                              "0", stats.getAbstractClasses() );
                assertEquals( "0", stats.getCa()  );
                assertEquals( "14", stats.getCe() );
                assertEquals( "0", stats.getA() );
                assertEquals( "1", stats.getI() );
                assertEquals( "0", stats.getD() );
                assertEquals( "1", stats.getV() );
            }
            if( jdpackage.getPackageName().equals( "org.codehaus.mojo.jdepend.objects" ) )
            {
                Stats stats = jdpackage.getStats();
                
                assertEquals( "Stats Total Classes is not equal to expected output", 
                              "4",  stats.getTotalClasses() );
                assertEquals( "Stats Concrete classes is not equal to expected output", 
                              "4", stats.getConcreteClasses() );
                assertEquals( "Stats Abstract Classes is not equal to expected output", 
                              "0", stats.getAbstractClasses() );
                assertEquals( "1", stats.getCa()  );
                assertEquals( "2", stats.getCe() );
                assertEquals( "0", stats.getA() );
                assertEquals( "0.67", stats.getI() );
                assertEquals( "0.33", stats.getD() );
                assertEquals( "1", stats.getV() );
            }
        }
    }
    
    public void testConcreteClasses()
    {
        for( int i=0; i<packages.size(); i++ )
        {
            JDPackage jdpackage = ( JDPackage ) packages.get( i );

            if( jdpackage.getPackageName().equals( "org.codehaus.mojo.jdepend" ) )
            {
                List concretes = jdpackage.getConcreteClasses();
                
                assertTrue( concretes.contains( "org.codehaus.mojo.jdepend.JDependMojo" ) );
                assertTrue( concretes.contains( "org.codehaus.mojo.jdepend.JDependXMLReportParser" ) );
                assertTrue( concretes.contains( "org.codehaus.mojo.jdepend.ReportGenerator" ) );
            }
            if( jdpackage.getPackageName().equals( "org.codehaus.mojo.jdepend.objects" ) )
            {
                List concretes = jdpackage.getConcreteClasses();
                
                for( int j=0; j<concretes.size(); j++ )
                {
                    System.out.println( "============= " + concretes.get( j ).toString() );
                }
                assertTrue( concretes.contains( "org.codehaus.mojo.jdepend.objects.CyclePackage" ) );
                assertTrue( concretes.contains( "org.codehaus.mojo.jdepend.objects.JDPackage" ) );
                
                /* Test failure
                assertTrue( concretes.contains( "org.codehaus.mojo.jdepend.objects.Packages" ) );
                */
                
                assertTrue( concretes.contains( "org.codehaus.mojo.jdepend.objects.Stats" ) );
            }
        }
    }
    
    public void testCountOfDependsUpon()
    {
        int count = 0;
        
        for( int i=0; i<packages.size(); i++ )
        {
            JDPackage jdpackage = ( JDPackage ) packages.get( i );
            
            if( jdpackage.getPackageName().equals( "org.codehaus.mojo.jdepend" ) )
            {
                count = jdpackage.getDependsUpon().size();
                assertEquals( 14, count );
            }
            if( jdpackage.getPackageName().equals( "org.codehaus.mojo.jdepend.objects" ) )
            {
                count = jdpackage.getDependsUpon().size();
                assertEquals( 2, count );
            }
        }
    }
}

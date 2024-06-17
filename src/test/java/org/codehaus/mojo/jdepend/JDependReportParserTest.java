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
import java.util.ArrayList;
import java.util.List;

import org.codehaus.mojo.jdepend.objects.JDPackage;
import org.codehaus.mojo.jdepend.objects.Stats;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JDependReportParserTest {
    private JDependXMLReportParser parser;

    private List<JDPackage> packages;

    private final String basedir = System.getProperty("basedir");

    @Before
    public void setUp() throws Exception {
        File reportXML = new File(basedir, JDependMojoTest.REPORT_PATH);
        parser = new JDependXMLReportParser(reportXML);

        packages = parser.packages;
    }

    @Test
    public void testTotalNumberPackages() {
        assertEquals("Total number of packages is not equal to expected output", 3, parser.packages.size());
    }

    @Test
    public void testPackageNames() {
        List<String> packageNames = new ArrayList<>();

        for (JDPackage jdpackage : packages) {
            packageNames.add(jdpackage.getPackageName());
        }
        assertTrue(packageNames.contains("org.codehaus.mojo.jdepend"));
        assertTrue(packageNames.contains("org.codehaus.mojo.jdepend.objects"));
    }

    @Test
    public void testPackageNamesNotContainingInList() {
        List<String> packageNames = new ArrayList<>();

        for (JDPackage jdpackage : packages) {
            packageNames.add(jdpackage.getPackageName());
        }
        assertFalse(packageNames.contains("test"));
        assertFalse(packageNames.contains("test2"));
    }

    @Test
    public void testStats() {
        for (JDPackage jdpackage : packages) {
            if (jdpackage.getPackageName().equals("org.codehaus.mojo.jdepend")) {
                Stats stats = jdpackage.getStats();

                assertEquals("Stats Total Classes is not equal to expected output", "5", stats.getTotalClasses());
                assertEquals("Stats Concrete classes is not equal to expected output", "4", stats.getConcreteClasses());
                assertEquals("Stats Abstract Classes is not equal to expected output", "1", stats.getAbstractClasses());
                assertEquals("0", stats.getCa());
                assertEquals("14", stats.getCe());
                assertEquals("0.2", stats.getA());
                assertEquals("1", stats.getI());
                assertEquals("0.2", stats.getD());
                assertEquals("1", stats.getV());
            }
            if (jdpackage.getPackageName().equals("org.codehaus.mojo.jdepend.objects")) {
                Stats stats = jdpackage.getStats();

                assertEquals("Stats Total Classes is not equal to expected output", "4", stats.getTotalClasses());
                assertEquals("Stats Concrete classes is not equal to expected output", "4", stats.getConcreteClasses());
                assertEquals("Stats Abstract Classes is not equal to expected output", "0", stats.getAbstractClasses());
                assertEquals("1", stats.getCa());
                assertEquals("2", stats.getCe());
                assertEquals("0", stats.getA());
                assertEquals("0.67", stats.getI());
                assertEquals("0.33", stats.getD());
                assertEquals("1", stats.getV());
            }
        }
    }

    @Test
    public void testConcreteClasses() {
        for (JDPackage jdpackage : packages) {
            if (jdpackage.getPackageName().equals("org.codehaus.mojo.jdepend")) {
                List<String> concretes = jdpackage.getConcreteClasses();

                assertTrue(concretes.contains("org.codehaus.mojo.jdepend.JDependMojo"));
                assertTrue(concretes.contains("org.codehaus.mojo.jdepend.JDependXMLReportParser"));
                assertTrue(concretes.contains("org.codehaus.mojo.jdepend.ReportGenerator"));
            }
            if (jdpackage.getPackageName().equals("org.codehaus.mojo.jdepend.objects")) {
                List<String> concretes = jdpackage.getConcreteClasses();

                assertTrue(concretes.contains("org.codehaus.mojo.jdepend.objects.CyclePackage"));
                assertTrue(concretes.contains("org.codehaus.mojo.jdepend.objects.JDPackage"));

                /*
                 * Test failure assertTrue( concretes.contains( "org.codehaus.mojo.jdepend.objects.Packages" ) );
                 */

                assertTrue(concretes.contains("org.codehaus.mojo.jdepend.objects.Stats"));
            }
        }
    }

    @Test
    public void testCountOfDependsUpon() {
        for (JDPackage jdpackage : packages) {
            if (jdpackage.getPackageName().equals("org.codehaus.mojo.jdepend")) {
                int count = jdpackage.getDependsUpon().size();
                assertEquals(14, count);
            }
            if (jdpackage.getPackageName().equals("org.codehaus.mojo.jdepend.objects")) {
                int count = jdpackage.getDependsUpon().size();
                assertEquals(2, count);
            }
        }
    }
}

/*
 * JDependXMLReportParserTest.java
 * JUnit based test
 *
 * Created on August 4, 2005, 1:21 PM
 */

package com.mergere;

import junit.framework.*;
import com.mergere.objects.JDPackage;
import com.mergere.objects.Packages;
import com.mergere.objects.Stats;
import java.io.File;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import com.mergere.JDependXMLReportParser;
import com.mergere.objects.*;

public class JDependXMLReportParserTest extends TestCase 
{
    JDependXMLReportParser jdepend;
    
    JDPackage jdPackage;
    
    Stats stats;
    
    public JDependXMLReportParserTest(String testName) 
    {
        super(testName);
    }

    protected void setUp() throws Exception 
    {
        System.out.println("SETTING UP TEST >>>");
        String xmlFile = System.getProperty("basedir") + "/src/test/resources/report.xml";
        jdepend = new JDependXMLReportParser(xmlFile);
    } 
    
    public void testPackages()
    {
        //System.out.println("Testing XML Parser...");
        
        List list = jdepend.packages;
        
        assertEquals("Assertion Fails, Packages size not equal",
                1, list.size());
    }
    
    public void testPackage()
    {
        List list = jdepend.packages;
        
        jdPackage = (JDPackage) list.get(0);
        
        stats = (Stats) jdPackage.getStats();
        
        assertEquals("Wrong Assestment on package name", 
                "com.mergere", jdPackage.getPackageName());
        
        assertEquals("Wrong Assestment on Stats Total Classes",
                "2", stats.getTotalClasses());
        
        assertEquals("Wrong Assestment on Stats Concrete Classes",
                "2", stats.getConcreteClasses());
        
        assertEquals("Wrong Assestment on Stats Abstract Classes",
                "0", stats.getAbstractClasses());
    
        assertEquals("Wrong Assestment on Stats Afferent Couplings",
                "0", stats.getCa());
        
        assertEquals("Wrong Assestment on Stats Efferent Couplings",
                "6", stats.getCe());
        
        assertEquals("Wrong Assestment on Stats Abstractness",
                "0", stats.getA());
        
        assertEquals("Wrong Assestment on Stats Instability",
                "1", stats.getI());
    
        assertEquals("Wrong Assestment on Stats Distance",
                "0", stats.getD());
        
        assertEquals("Wrong Assestment on Stats V",
                "1", stats.getV());
        
        
        List abstractClasses = jdPackage.getAbstractClasses();
        
        assertEquals("Wrong Assestment on No. of Abstract", 
                0, jdPackage.getAbstractClasses().size());
        
        List concreteClasses = jdPackage.getConcreteClasses();
        
        assertEquals("Wrong Assestment on No. of Concrete Classes",
                2, jdPackage.getConcreteClasses().size());
        
        assertEquals("Wrong value in concrete Classes @ index zero",
                "com.mergere.JDependXMLGenerator", jdPackage.getConcreteClasses().get(0).toString());
        
        assertEquals("Wrong value in concrete Classes @ index one",
                "com.mergere.JDependXMLGeneratorTest", jdPackage.getConcreteClasses().get(1).toString());
    
        
        List dependsUponList = jdPackage.getDependsUpon();
        
        assertEquals(6, dependsUponList.size());
        
        assertEquals("java.io", dependsUponList.get(0).toString());
        
        assertEquals("java.lang", dependsUponList.get(1).toString());
        
        assertEquals("java.util", dependsUponList.get(2).toString());
        
        assertEquals("jdepend.xmlui", dependsUponList.get(3).toString());
        
        assertEquals("junit.framework", dependsUponList.get(4).toString());
        
        assertEquals("org.apache.maven.plugin", dependsUponList.get(5).toString());
    }
}



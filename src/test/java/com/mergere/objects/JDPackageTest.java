/*
 * JDPackageTest.java
 * JUnit based test
 *
 * Created on August 10, 2005, 12:59 PM
 */

package com.mergere.objects;

import junit.framework.*;
import java.util.ArrayList;
import java.util.List;

import com.mergere.objects.JDPackage;

public class JDPackageTest extends TestCase 
{
    JDPackage jdPackage;
    
    public JDPackageTest(String testName) 
    {
        super(testName);
    }

    protected void setUp() throws Exception 
    {
        jdPackage = new JDPackage();
    }

    public void testPackageName() 
    {
        jdPackage.setPackageName("com.mergere.test");
        assertEquals("Wrong Assestment", "com.mergere.test", jdPackage.getPackageName());
    }

    public void testAbstractClassesWithOneRecord() 
    {
        
        jdPackage.addAbstractClasses("one");
        assertEquals("Wrong Assestment, abstract classes with one record", 
                1, jdPackage.getAbstractClasses().size());
    }
    
    public void testAbstractClassesWithManyRecords()
    {
        for(int i=0; i<100; i++)
        {
            jdPackage.addAbstractClasses(new Integer(i));
        }
        assertEquals("Wrong Assestment abstract classes with many records", 
                100, jdPackage.getAbstractClasses().size());
    }
    
    public void testAbstractClassesValueWithIndexZero()
    {
        jdPackage.addAbstractClasses("test");
        assertEquals("Wrong Assestment, abstract class value in index zero", 
                "test", jdPackage.getAbstractClasses().get(0));
    }
    
    public void testAbstractClassesValueWithIndexOne()
    {
        for(int i=0; i<5; i++)
        {
            jdPackage.addAbstractClasses(new Integer(i));
        }
        assertEquals("Wrong Assestment, abstract class value in index one",
                  "1", jdPackage.getAbstractClasses().get(1).toString());
    }

    public void testConcreteClassesWithOneRecord() 
    {
        jdPackage.addConcreteClasses("one");
        assertEquals("Wrong Assestment, concrete classes with one record", 
                    1, jdPackage.getConcreteClasses().size());
    }

    public void testConcreteClassesWithManyRecords() 
    {
        for(int i=0; i<100; i++)
        {
            jdPackage.addConcreteClasses(new Integer(i));
        }
        assertEquals("Wrong Assestment, concrete classes with many record",
                100, jdPackage.getConcreteClasses().size());
    }

    public void testDependsUponWithOneRecord() 
    {
        jdPackage.addDependsUpon("one");
        assertEquals("Wrong Assestment, depends upon with one record",
                1, jdPackage.getDependsUpon().size());
    }

    public void testDependsUponWithManyRecord() 
    {
        for(int i=0; i < 50; i++)
        {
            jdPackage.addDependsUpon(new Integer(i));
        }
        assertEquals("Wrong Assestment, depends upon with many record",
                        50, jdPackage.getDependsUpon().size());
    }

    public void testUsedByWithOneRecord() 
    {
        jdPackage.addUsedBy("one");
        assertEquals("Wrong Assestment, used by with one record",
                    1, jdPackage.getUsedBy().size());
    }    
}

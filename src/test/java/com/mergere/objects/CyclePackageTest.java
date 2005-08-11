/*
 * CyclePackageTest.java
 * JUnit based test
 *
 * Created on August 10, 2005, 11:23 AM
 */

package com.mergere.objects;

import junit.framework.*;
import java.util.ArrayList;
import java.util.List;

import com.mergere.objects.CyclePackage;

public class CyclePackageTest extends TestCase 
{
    CyclePackage cyclePackage;
    
    public CyclePackageTest(String testName) 
    {
        super(testName);
    }

    protected void setUp() throws Exception 
    {
        cyclePackage = new CyclePackage();
    }

    public void testPackageListWithOneRecord() 
    {
        cyclePackage.addPackageList("One");
        assertEquals(1, cyclePackage.getPackageList().size());
    }
    
    public void testPackageListWithManyRecord() 
    {
        for(int i=0; i<15; i++)
        {
            cyclePackage.addPackageList(new Integer(i));
        }
        assertEquals(15, cyclePackage.getPackageList().size());
    }
    
    public void testPackageListValue()
    {
        cyclePackage.addPackageList("test");
        assertEquals("Wrong Assestment ", "test", cyclePackage.getPackageList().get(0).toString());
    }

    public void testName() 
    {
        cyclePackage.setName("com.mergere.test");
        assertEquals("Wrong Assestment ", "com.mergere.test", cyclePackage.getName());
    }
    
}

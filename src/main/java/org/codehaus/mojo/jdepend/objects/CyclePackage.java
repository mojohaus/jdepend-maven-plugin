/*
 * Cycles.java
 *
 * Created on August 9, 2005, 4:29 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.codehaus.mojo.jdepend.objects;

import java.util.ArrayList;
import java.util.List;


public class CyclePackage 
{
    private List packageList;
    
    private String name;
    
    /** Creates a new instance of Cycles */
    public CyclePackage() 
    {
    }
    
    public List getPackageList()
    {
        if(packageList == null)
        {
            packageList = new ArrayList();
        }
        return this.packageList;
    }
    
    public void addPackageList(Object object)
    {
        getPackageList().add(object);
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }
}

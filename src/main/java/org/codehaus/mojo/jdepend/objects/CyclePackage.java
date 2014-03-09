package org.codehaus.mojo.jdepend.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Who ever this implemented first.
 */
public class CyclePackage
{
    private List<String> packageList;

    private String name;

    /**
     * Creates a new instance of Cycles.
     */
    public CyclePackage()
    {
    }

    public List<String> getPackageList()
    {
        if ( packageList == null )
        {
            packageList = new ArrayList<String>();
        }
        return this.packageList;
    }

    public void addPackageList( String object )
    {
        getPackageList().add( object );
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }
}

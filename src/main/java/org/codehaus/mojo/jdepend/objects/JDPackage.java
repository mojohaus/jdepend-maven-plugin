package org.codehaus.mojo.jdepend.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Who ever this implemented first.
 */
public class JDPackage
{
    /* Elements */
    private Stats stats;

    private String packageName;

    private List<String> abstractClasses;

    private List<String> concreteClasses;

    private List<String> dependsUpon;

    private List<String> usedBy;

    /**
     * Creates a new instance of JDPackage.
     */
    public JDPackage()
    {
    }

    public String getPackageName()
    {
        return this.packageName;
    }

    public void setPackageName( String name )
    {
        this.packageName = name;
    }

    public Stats getStats()
    {
        return stats;
    }

    public void setStats( Stats stats )
    {
        this.stats = stats;
    }

    public List<String> getAbstractClasses()
    {
        if ( abstractClasses == null )
        {
            abstractClasses = new ArrayList<String>();
        }
        return abstractClasses;
    }

    public void addAbstractClasses( String object )
    {
        getAbstractClasses().add( object );
    }

    public List<String> getConcreteClasses()
    {
        if ( concreteClasses == null )
        {
            concreteClasses = new ArrayList<String>();
        }
        return concreteClasses;
    }

    public void addConcreteClasses( String object )
    {
        getConcreteClasses().add( object );
    }

    public List<String> getDependsUpon()
    {
        if ( dependsUpon == null )
        {
            dependsUpon = new ArrayList<String>();
        }
        return dependsUpon;
    }

    public void addDependsUpon( String object )
    {
        getDependsUpon().add( object );
    }

    public void addUsedBy( String object )
    {
        getUsedBy().add( object );
    }

    public List<String> getUsedBy()
    {
        if ( usedBy == null )
        {
            usedBy = new ArrayList<String>();
        }
        return usedBy;
    }
}

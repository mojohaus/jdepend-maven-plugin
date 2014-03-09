package org.codehaus.mojo.jdepend.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Who ever this implemented first.
 */
public class Packages
{
    private List<JDPackage> jdpackage;

    /**
     * Creates a new instance of Packages.
     */
    public Packages()
    {
    }

    /**
     * @return packages.
     */
    public List<JDPackage> getPackages()
    {
        if ( this.jdpackage == null )
        {
            this.jdpackage = new ArrayList<JDPackage>();
        }

        return this.jdpackage;
    }

    /**
     * @param jdpackage The List of JDPackage
     */
    public void setPackages( List<JDPackage> jdpackage )
    {
        this.jdpackage = jdpackage;
    }

    /**
     * @param jdpackage Add a single package.
     */
    public void addPackage( JDPackage jdpackage )
    {
        getPackages().add( jdpackage );
    }

}

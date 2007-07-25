package org.codehaus.mojo.jdepend.objects;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.ArrayList;
import java.util.List;

public class JDPackage
{
    /* Elements */
    private Stats stats;

    private String packageName;

    private List abstractClasses;

    private List concreteClasses;

    private List dependsUpon;

    private List usedBy;

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

    public List getAbstractClasses()
    {
        if ( abstractClasses == null )
        {
            abstractClasses = new ArrayList();
        }
        return abstractClasses;
    }

    public void addAbstractClasses( Object object )
    {
        getAbstractClasses().add( object );
    }

    public List getConcreteClasses()
    {
        if ( concreteClasses == null )
        {
            concreteClasses = new ArrayList();
        }
        return concreteClasses;
    }

    public void addConcreteClasses( Object object )
    {
        getConcreteClasses().add( object );
    }

    public List getDependsUpon()
    {
        if ( dependsUpon == null )
        {
            dependsUpon = new ArrayList();
        }
        return dependsUpon;
    }

    public void addDependsUpon( Object object )
    {
        getDependsUpon().add( object );
    }

    public void addUsedBy( Object object )
    {
        getUsedBy().add( object );
    }

    public List getUsedBy()
    {
        if ( usedBy == null )
        {
            usedBy = new ArrayList();
        }
        return usedBy;
    }
}

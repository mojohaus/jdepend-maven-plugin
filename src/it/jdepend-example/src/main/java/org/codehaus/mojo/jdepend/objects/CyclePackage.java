package org.codehaus.mojo.jdepend.objects;

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
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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

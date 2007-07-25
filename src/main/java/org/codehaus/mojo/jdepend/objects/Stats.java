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

public class Stats
{
    private String totalClasses;

    private String concreteClasses;

    private String abstractClasses;

    private String ca;

    private String ce;

    private String a;

    private String i;

    private String d;

    private String v;

    public String getTotalClasses()
    {
        return totalClasses;
    }

    public void setTotalClasses( String totalClasses )
    {
        this.totalClasses = totalClasses;
    }

    public String getConcreteClasses()
    {
        return concreteClasses;
    }

    public void setConcreteClasses( String concreteClasses )
    {
        this.concreteClasses = concreteClasses;
    }

    public String getAbstractClasses()
    {
        return abstractClasses;
    }

    public void setAbstractClasses( String abstractClasses )
    {
        this.abstractClasses = abstractClasses;
    }

    public String getCa()
    {
        return ca;
    }

    public void setCa( String ca )
    {
        this.ca = ca;
    }

    public String getCe()
    {
        return ce;
    }

    public void setCe( String ce )
    {
        this.ce = ce;
    }

    public String getA()
    {
        return a;
    }

    public void setA( String a )
    {
        this.a = a;
    }

    public String getI()
    {
        return i;
    }

    public void setI( String i )
    {
        this.i = i;
    }

    public String getD()
    {
        return d;
    }

    public void setD( String d )
    {
        this.d = d;
    }

    public String getV()
    {
        return v;
    }

    public void setV( String v )
    {
        this.v = v;
    }
}

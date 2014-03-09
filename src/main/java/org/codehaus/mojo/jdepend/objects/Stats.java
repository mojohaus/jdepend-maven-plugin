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

/**
 * @author Who ever this implemented first.
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

    /**
     * @return total classes.
     */
    public String getTotalClasses()
    {
        return totalClasses;
    }

    /**
     * @param totalClasses
     */
    public void setTotalClasses( String totalClasses )
    {
        this.totalClasses = totalClasses;
    }

    /**
     * @return concrete classes.
     */
    public String getConcreteClasses()
    {
        return concreteClasses;
    }

    /**
     * @param concreteClasses
     */
    public void setConcreteClasses( String concreteClasses )
    {
        this.concreteClasses = concreteClasses;
    }

    /**
     * @return get abstract classes.
     */
    public String getAbstractClasses()
    {
        return abstractClasses;
    }

    /**
     * @param abstractClasses
     */
    public void setAbstractClasses( String abstractClasses )
    {
        this.abstractClasses = abstractClasses;
    }

    /**
     * @return ca.
     */
    public String getCa()
    {
        return ca;
    }

    /**
     * @param ca
     */
    public void setCa( String ca )
    {
        this.ca = ca;
    }

    /**
     * @return Ce
     */
    public String getCe()
    {
        return ce;
    }

    public void setCe( String ce )
    {
        this.ce = ce;
    }

    /**
     * @return A
     */
    public String getA()
    {
        return a;
    }

    /**
     * @param a
     */
    public void setA( String a )
    {
        this.a = a;
    }

    /**
     * @return I
     */
    public String getI()
    {
        return i;
    }

    /**
     * @param i
     */
    public void setI( String i )
    {
        this.i = i;
    }

    /**
     * @return D
     */
    public String getD()
    {
        return d;
    }

    /**
     * @param d
     */
    public void setD( String d )
    {
        this.d = d;
    }

    /**
     * @return V
     */
    public String getV()
    {
        return v;
    }

    /**
     * @param v
     */
    public void setV( String v )
    {
        this.v = v;
    }
}

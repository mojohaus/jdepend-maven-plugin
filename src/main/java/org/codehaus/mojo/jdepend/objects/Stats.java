
/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
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
 */

package org.codehaus.mojo.jdepend.objects;


public class Stats 
{
    private String totalClasses;
    
    private String concreteClasses;
    
    private String abstractClasses;
    
    private String Ca;
    
    private String Ce;
    
    private String A;
    
    private String I;
    
    private String D;
    
    private String V;

    public String getTotalClasses() 
    {
        return totalClasses;
    }

    public void setTotalClasses(String totalClasses) 
    {
        this.totalClasses = totalClasses;
    }

    public String getConcreteClasses() 
    {
        return concreteClasses;
    }

    public void setConcreteClasses(String concreteClasses) 
    {
        this.concreteClasses = concreteClasses;
    }

    public String getAbstractClasses() 
    {
        return abstractClasses;
    }

    public void setAbstractClasses(String abstractClasses) 
    {
        this.abstractClasses = abstractClasses;
    }

    public String getCa() 
    {
        return Ca;
    }

    public void setCa(String Ca) 
    {
        this.Ca = Ca;
    }

    public String getCe() 
    {
        return Ce;
    }

    public void setCe(String Ce) 
    {
        this.Ce = Ce;
    }

    public String getA() 
    {
        return A;
    }

    public void setA(String A) 
    {
        this.A = A;
    }

    public String getI() 
    {
        return I;
    }

    public void setI(String I) 
    {
        this.I = I;
    }

    public String getD() 
    {
        return D;
    }

    public void setD(String D) 
    {
        this.D = D;
    }

    public String getV() 
    {
        return V;
    }

    public void setV(String V) 
    {
        this.V = V;
    }
    
    
}

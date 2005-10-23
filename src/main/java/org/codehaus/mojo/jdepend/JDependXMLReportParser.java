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

package org.codehaus.mojo.jdepend;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.codehaus.mojo.jdepend.objects.CyclePackage;
import org.codehaus.mojo.jdepend.objects.JDPackage;
import org.codehaus.mojo.jdepend.objects.Stats;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class JDependXMLReportParser extends DefaultHandler
{
    protected List packages;
     
    protected JDPackage jdpackage;
    
    protected Stats stats;
    
    protected String elementValue;
    
    protected Stack stack;
    
    protected List cycles;
    
    protected CyclePackage cyclePackage;
    
    ReportGenerator report;
    
    private boolean errFlag = false;
    
    /** Creates a new instance of JDependXMLReportParser */
    public JDependXMLReportParser( String xmlPath )
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try 
        {
            /* Create an empty stack */
            stack = new Stack();
            
            SAXParser saxParser = factory.newSAXParser();
            
            saxParser.parse( new File( xmlPath ),this );
            
            //report = new ReportGenerator(concreteClasses);
        } 
        catch (Throwable t) 
        {
           t.printStackTrace();
        }
        
    }
    
    public void startElement(String namespaceURI, 
            String sName,
            String qName, 
            Attributes attrs)
       throws SAXException
    {
        //System.out.println("START qname: " + qName);
        
        /* Push element name into stack */
        stack.push(qName);
        
        if(qName.equals("Packages"))
        {      
            packages = new ArrayList();
        }
        else if(qName.equals("Package"))
        {
            if(isParentElement("Packages"))
            {
                jdpackage = new JDPackage();
                
                if(attrs != null)
                {
                    jdpackage.setPackageName(attrs.getValue(0));
                }
            }
            else if(isParentElement("Cycles"))
            {
                cyclePackage = new CyclePackage();
                
                if(attrs != null)
                {
                    cyclePackage.setName(attrs.getValue(0));
                }
            }
        }
        else if(qName.equals("Stats"))
        {
            stats = new Stats();
        }
        else if(qName.equals("Cycles"))
        {
            cycles = new ArrayList();
        }
    }
    
    public void endElement(String namespaceURI,
            String sName,
            String qName)
       throws SAXException
    {
        //System.out.println("END qname: /" + qName);
       
        if(qName.equals("Package"))
        {
            if(isParentElement("Packages"))
            {
                if(errFlag == false)
                {
                    jdpackage.setStats(stats);

                    packages.add(jdpackage);
                }
                errFlag = false;
            }
            else if(isParentElement("DependsUpon"))
            {
                jdpackage.addDependsUpon(elementValue);
            }
            else if(isParentElement("UsedBy"))
            {
                jdpackage.addUsedBy(elementValue);
            }
            else if(isParentElement("Package"))
            {
                cyclePackage.addPackageList(elementValue);
            }
            else if(isParentElement("Cycles"))
            {
                cycles.add(cyclePackage);
            }
        }   
        else if(qName.equals("TotalClasses"))
        {
            stats.setTotalClasses(elementValue);
        }
        else if(qName.equals("ConcreteClasses"))
        {    
            if(isParentElement("Stats"))
            {
                stats.setConcreteClasses(elementValue);
            }
        }
        else if(qName.equals("AbstractClasses"))
        {
            if(isParentElement("Stats"))
            {
                stats.setAbstractClasses(elementValue);
            }
        }
        else if(qName.equals("Ca"))
        {
            stats.setCa(elementValue);   
        }
        else if(qName.equals("Ce"))
        {
            stats.setCe(elementValue);
        }
        else if(qName.equals("A"))
        {
            stats.setA(elementValue);
        }
        else if(qName.equals("I"))
        {
            stats.setI(elementValue);
        }
        else if(qName.equals("D"))
        {      
            stats.setD(elementValue);
        }
        else if(qName.equals("V"))
        {
            stats.setV(elementValue);
        }
        else if(qName.equals("Class"))
        {
            if(isParentElement("AbstractClasses"))
            {
                jdpackage.addAbstractClasses(elementValue);
            }
            else if(isParentElement("ConcreteClasses"))
            {
                jdpackage.addConcreteClasses(elementValue);
            }
        }
        else if(qName.equals("error"))
        {
            if(isParentElement("Package"))
            {
                errFlag = true;
            }
        }
        
        
        if(stack.size() != 0)
        {
            /* Remove element name in stack */
            stack.pop();
        }
    }
    
    public void characters(char[] buff, int offset, int len)
        throws SAXException
    {
        String str = new String(buff, offset, len);
       
        if(!str.trim().equals(""))
        {
            elementValue = str.trim();
            //System.out.println("VALUE: " + elementValue); 
        }
    }
    
    public java.util.List getPackages()
    {
        return this.getPackages();
    }
    
    public Stats getStats()
    {
        return this.stats;
    }
    
    private int getParentIndex()
    {
        int parentIndex = 0;
        
        parentIndex = stack.size() - 2;
        
        return parentIndex;
    }
    
    private boolean isParentElement(String parentElement)
    {
        boolean isParent = false;
        
        isParent = stack.get(getParentIndex()).toString().equals(parentElement);
        
        return isParent;
    }
}

package org.codehaus.mojo.jdepend;

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
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.codehaus.mojo.jdepend.objects.CyclePackage;
import org.codehaus.mojo.jdepend.objects.JDPackage;
import org.codehaus.mojo.jdepend.objects.Stats;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Who ever this implemented first.
 */
public class JDependXMLReportParser extends DefaultHandler {
    protected List<JDPackage> packages;

    protected JDPackage jdpackage;

    protected Stats stats;

    protected StringBuffer buffer = null;

    protected Stack<String> stack;

    protected List<CyclePackage> cycles;

    protected CyclePackage cyclePackage;

    ReportGenerator report;

    private boolean errFlag = false;

    /**
     * Creates a new instance of JDependXMLReportParser.
     *
     * @param xmlFile
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public JDependXMLReportParser(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        /* Create an empty stack */
        stack = new Stack<>();

        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, false);
        saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, false);
        saxParser.parse(xmlFile, this);
    }

    /*
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String,
     * org.xml.sax.Attributes)
     */
    public void startElement(String namespaceURI, String sName, String qName, Attributes attrs) {

        /* Push element name into stack */
        stack.push(qName);

        // TODO only create a new buffer when the element is expected to have
        // text
        buffer = new StringBuffer();

        switch (qName) {
            case "Packages":
                packages = new ArrayList<>();
                break;
            case "Package":
                if (isParentElement("Packages")) {
                    jdpackage = new JDPackage();

                    if (attrs != null) {
                        jdpackage.setPackageName(attrs.getValue(0));
                    }
                } else if (isParentElement("Cycles")) {
                    cyclePackage = new CyclePackage();

                    if (attrs != null) {
                        cyclePackage.setName(attrs.getValue(0));
                    }
                }
                break;
            case "Stats":
                stats = new Stats();
                break;
            case "Cycles":
                cycles = new ArrayList<>();
                break;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement(String namespaceURI, String sName, String qName) {

        String elementValue = buffer != null ? buffer.toString().trim() : null;

        switch (qName) {
            case "Package":
                if (isParentElement("Packages")) {
                    if (!errFlag) {
                        jdpackage.setStats(stats);

                        packages.add(jdpackage);
                    }
                    errFlag = false;
                } else if (isParentElement("DependsUpon")) {
                    jdpackage.addDependsUpon(elementValue);
                } else if (isParentElement("UsedBy")) {
                    jdpackage.addUsedBy(elementValue);
                } else if (isParentElement("Package")) {
                    cyclePackage.addPackageList(elementValue);
                } else if (isParentElement("Cycles")) {
                    cycles.add(cyclePackage);
                }
                break;
            case "TotalClasses":
                stats.setTotalClasses(elementValue);
                break;
            case "ConcreteClasses":
                if (isParentElement("Stats")) {
                    stats.setConcreteClasses(elementValue);
                }
                break;
            case "AbstractClasses":
                if (isParentElement("Stats")) {
                    stats.setAbstractClasses(elementValue);
                }
                break;
            case "Ca":
                stats.setCa(elementValue);
                break;
            case "Ce":
                stats.setCe(elementValue);
                break;
            case "A":
                stats.setA(elementValue);
                break;
            case "I":
                stats.setI(elementValue);
                break;
            case "D":
                stats.setD(elementValue);
                break;
            case "V":
                stats.setV(elementValue);
                break;
            case "Class":
                if (isParentElement("AbstractClasses")) {
                    jdpackage.addAbstractClasses(elementValue);
                } else if (isParentElement("ConcreteClasses")) {
                    jdpackage.addConcreteClasses(elementValue);
                }
                break;
            case "error":
                if (isParentElement("Package")) {
                    errFlag = true;
                }
                break;
        }

        if (stack.size() != 0) {
            /* Remove element name in stack */
            stack.pop();
        }

        buffer = null;
    }

    /*
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    public void characters(char[] buff, int offset, int len) {
        if (buffer != null) {
            buffer.append(buff, offset, len);
        }
    }

    /**
     * @return Packages.
     */
    public List<JDPackage> getPackages() {
        return this.packages;
    }

    /**
     * @return stats.
     */
    public Stats getStats() {
        return this.stats;
    }

    /**
     * @return parent index.
     */
    private int getParentIndex() {
        return stack.size() - 2;
    }

    /**
     * @param parentElement
     * @return true otherwise false.
     */
    private boolean isParentElement(String parentElement) {
        return stack.get(getParentIndex()).equals(parentElement);
    }
}

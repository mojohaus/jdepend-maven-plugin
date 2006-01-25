package org.codehaus.mojo.jdepend;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import jdepend.xmlui.JDepend;
import junit.framework.TestCase;

public class JDependMojoTest
    extends TestCase
{
    JDependXMLReportParser parser;
    
    public static final String REPORT_PATH = "target/test-classes/jdepend-report.xml";

    private String basedir = System.getProperty( "basedir" );

    private File reportXML = new File( basedir, REPORT_PATH );

    public void setUp() throws Exception
    {
        parser = new JDependXMLReportParser( reportXML );
    }
        
    public void testJDependReportContent()
        throws IOException
    {
        File generatedReport = new File( basedir, "target/jdepend-report.xml" );
        File classDirectory = new File( basedir, "target/classes" );
        
        String[] args = new String[3];
        
        args[0] = "-file";
        args[1] = generatedReport.getCanonicalPath();
        args[2] = classDirectory.getCanonicalPath();
        
        JDepend.main( args );
        
        assertTrue( "Generated report xml is not equal to expected output",
                    compareFiles( generatedReport, reportXML ) );
    }
    
    public boolean compareFiles( File f1, File f2 )
        throws IOException
    {
        String content1 = getContent( f1 );
        
        String content2 = getContent( f2 );
        
        StringTokenizer tokenizer1 = new StringTokenizer( content1 );
        
        StringTokenizer tokenizer2 = new StringTokenizer( content2 );
        
        if( tokenizer1.countTokens() != tokenizer2.countTokens() )
        {
            return false;
        }
        
        while( tokenizer1.hasMoreTokens() )
        {
           if( !tokenizer1.nextToken().equalsIgnoreCase( tokenizer2.nextToken() ) )
           {
               return false;
           }
        }
        return true;
    }
    
    public String getContent( File file )
        throws IOException
    {
        FileInputStream fIn = new FileInputStream( file );
        
        byte bytes[] = new byte[ fIn.available() ];
        
        fIn.read( bytes );
        
        fIn.close();
        
        return new String( bytes );
    }
}

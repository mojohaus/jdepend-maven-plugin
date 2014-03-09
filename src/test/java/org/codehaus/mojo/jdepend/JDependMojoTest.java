package org.codehaus.mojo.jdepend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jdepend.xmlui.JDepend;
import junit.framework.TestCase;

import org.codehaus.plexus.util.IOUtil;

public class JDependMojoTest
    extends TestCase
{
    JDependXMLReportParser parser;

    public static final String REPORT_PATH = "target/test-classes/jdepend-report.xml";

    private String basedir = System.getProperty( "basedir" );

    private File reportXML = new File( basedir, REPORT_PATH );

    public void setUp()
        throws Exception
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
        ;
        assertTrue( "Generated report xml from " + generatedReport + " is not equal to expected output " + reportXML,
                    IOUtil.contentEquals( new FileInputStream( generatedReport ), new FileInputStream( reportXML ) ) );
    }

}

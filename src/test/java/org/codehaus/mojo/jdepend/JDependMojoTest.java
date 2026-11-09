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

import java.io.File;

import jdepend.xmlui.JDepend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class JDependMojoTest {
    JDependXMLReportParser parser;

    public static final String REPORT_PATH = "target/test-classes/jdepend-report.xml";

    private final String basedir = System.getProperty("basedir");

    private final File reportXML = new File(basedir, REPORT_PATH);

    @BeforeEach
    void setUp() throws Exception {
        parser = new JDependXMLReportParser(reportXML);
    }

    @Test
    void jDependReportContent() throws Exception {
        File generatedReport = new File(basedir, "target/jdepend-report.xml");
        File classDirectory = new File(basedir, "target/classes");

        String[] args = new String[3];

        args[0] = "-file";
        args[1] = generatedReport.getCanonicalPath();
        args[2] = classDirectory.getCanonicalPath();

        JDepend.main(args);

        Diff myDiff = DiffBuilder.compare(Input.fromFile(reportXML))
                .withTest(Input.fromFile(generatedReport))
                .ignoreComments()
                .build();

        assertFalse(myDiff.hasDifferences(), myDiff.toString());
    }
}

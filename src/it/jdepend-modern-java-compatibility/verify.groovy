
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

def xmlReport = new File( basedir,'target/jdepend-report.xml' )
assert xmlReport.exists()

def siteReport = new File( basedir,'target/site/jdepend-report.html' )
assert siteReport.exists()

def xml = xmlReport.getText( 'UTF-8' )
def html = siteReport.getText( 'UTF-8' )


assert html.contains( 'org.codehaus.mojo.modernjava' )

assert xml.contains( 'org.codehaus.mojo.modernjava' )
assert xml.contains( 'org.codehaus.mojo.modernjava.another' )
assert xml.contains( 'org.codehaus.mojo.modernjava.another.Another' )
assert xml.contains( 'org.codehaus.mojo.modernjava.LambdaSample' )
assert xml.contains( 'org.codehaus.mojo.modernjava.RecordSample' )

// the classes were analyzed, not only referenced from another package
assert xml.contains( '<Class sourceFile="LambdaSample.java">' )
assert xml.contains( '<Class sourceFile="RecordSample.java">' )
assert !new File( basedir, 'build.log' ).text.contains( 'Unknown constant' )

assert html.contains( 'org.codehaus.mojo.modernjava' )
assert html.contains( 'org.codehaus.mojo.modernjava.another' )



return true

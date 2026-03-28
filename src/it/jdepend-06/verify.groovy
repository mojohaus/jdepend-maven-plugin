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

def report = new File(basedir, 'target/site/jdepend-report.html')
assert report.exists()

def html = report.getText('UTF-8')

assert (html =~ /(?s)<table\b[^>]*>.*?<caption>\s*Summary\s*<\/caption>/).find()
assert (html =~ /(?s)<caption>\s*Summary\s*<\/caption>.*?<th\b[^>]*>\s*Package\s*<\/th>/).find()
assert (html =~ /(?s)<caption>\s*Summary\s*<\/caption>.*?<th\b[^>]*>\s*TC\s*<\/th>/).find()
assert (html =~ /(?s)<caption>\s*Summary\s*<\/caption>.*?<td\b[^>]*>\s*(?:<a\b[^>]*>)?org\.codehaus\.mojo\.a(?:<\/a>)?\s*<\/td>/).find()
assert (html =~ /(?s)<caption>\s*Summary\s*<\/caption>.*?<td\b[^>]*>\s*(?:<a\b[^>]*>)?org\.codehaus\.mojo\.b(?:<\/a>)?\s*<\/td>/).find()
assert (html =~ /(?s)<caption>\s*Summary\s*<\/caption>.*?<td\b[^>]*>\s*(?:<a\b[^>]*>)?org\.codehaus\.mojo\.c(?:<\/a>)?\s*<\/td>/).find()
assert !(html =~ /Summary\s+Package\s+TC\s+CC\s+AC\s+Ca\s+Ce\s+A\s+I\s+D\s+V\s+org\.codehaus\.mojo\.a/).find()

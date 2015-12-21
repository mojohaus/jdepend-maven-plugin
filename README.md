# MojoHaus JDepend Maven Plugin

[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/mojohaus/jdepend-maven-plugin.svg?label=License)](http://www.apache.org/licenses/)
[![Maven Central](https://img.shields.io/maven-central/v/org.codehaus.mojo/jdepend-maven-plugin.svg?label=Maven%20Central)](http://search.maven.org/#search%7Cga%7C1%7Cjdepend-maven-plugin)
[![Build Status](https://travis-ci.org/mojohaus/jdepend-maven-plugin.svg?branch=master)](https://travis-ci.org/mojohaus/jdepend-maven-plugin)


The [jdepend-maven-plugin](http://www.mojohaus.org/jdepend-maven-plugin/)
plugin produces a nicely formatted metrics report based on your project.
 
## Releasing

* Make sure `gpg-agent` is running.
* Execute `mvn -B release:prepare release:perform`

For publishing the site do the following:

```
cd target/checkout
mvn verify site site:stage scm-publish:publish-scm
```

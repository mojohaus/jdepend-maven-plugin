# MojoHaus JDepend Maven Plugin

[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/mojohaus/jdepend-maven-plugin.svg?label=License)](https://www.apache.org/licenses/)
[![Maven Central](https://img.shields.io/maven-central/v/org.codehaus.mojo/jdepend-maven-plugin.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/org.codehaus.mojo/jdepend-maven-plugin)
[![Build Status](https://github.com/mojohaus/jdepend-maven-plugin/actions/workflows/maven-verify.yml/badge.svg?branch=master)](https://github.com/mojohaus/jdepend-maven-plugin/actions/workflows/maven-verify.yml?query=branch%3Amaster)

The [jdepend-maven-plugin](https://www.mojohaus.org/jdepend-maven-plugin/) produces a metrics report
on the package dependencies of a project with [JDepend](https://github.com/nidi3/jdepend), and its
`check` goal can fail the build when it finds package dependency cycles. It requires Maven 3.9.0 or
later and Java 8 or later.

Add the plugin to the `reporting` section of the POM and run `mvn site` to get the report, or bind
`jdepend:check` to the build; the [plugin site](https://www.mojohaus.org/jdepend-maven-plugin/)
documents both.

Conventions for contributors and agents are in [AGENTS.md](AGENTS.md).

## Releasing

* Make sure `gpg-agent` is running.
* Execute `mvn -B release:prepare release:perform`

For publishing the site do the following:

```
cd target/checkout
mvn verify site site:stage scm-publish:publish-scm
```


# MojoHaus JDepend Maven Plugin

This is the [jdepend-maven-plugin](http://www.mojohaus.org/jdepend-maven-plugin/).
 
[![Build Status](https://travis-ci.org/mojohaus/jdepend-maven-plugin.svg?branch=master)](https://travis-ci.org/mojohaus/jdepend-maven-plugin)

## Releasing

* Make sure `gpg-agent` is running.
* Execute `mvn -B release:prepare release:perform`

For publishing the site do the following:

```
cd target/checkout
mvn verify site site:stage scm-publish:publish-scm
```

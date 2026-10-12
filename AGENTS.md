# Agent guide for jdepend-maven-plugin

This file is for automated agents and human contributors working on this repository. It records
what the code and the POMs do not make obvious.

## Layout

This is a single-module Maven plugin and report. The goals are `generate` (`JDependMojo`, which
forks the `compile` phase first) and `generate-no-fork` (`JDependNoForkMojo`, bound to `site`).
Both extend `AbstractJDependMojo`, which runs JDepend and renders the report with
`ReportGenerator`; `JDependXMLReportParser` reads the XML result into the classes in `objects`.

- The site is built from `src/site/apt`; `.apt.vm` files go through Velocity.

## Build gates

- `spotless-maven-plugin` from mojo-parent runs during `process-sources`. Run `mvn spotless:apply`
  before committing. It formats Java with palantir-java-format, sorts the POM, and also reformats
  Markdown, including this file and the README.
- `license-maven-plugin` runs `check-file-header` on `src/main/java` and `src/test/java` against
  the `asf-2.0` header in `src/license`.
- The plugin compiles against `mavenVersion` (3.9.16) and declares a literal Maven 3.9.0 as its
  `<prerequisites>`. Raising `mavenVersion` must not raise the prerequisite.
- Fields of the mojo classes are plugin parameters; renaming one changes the plugin's
  configuration API.

## Unit tests

- JUnit 5 only, under `src/test/java`. `JDependMojoTest` runs JDepend on `target/classes` and
  compares its XML with `src/test/resources/jdepend-report.xml` using XMLUnit; `basedir` comes from the
  system property that Surefire sets.

## Integration tests

- They live in `src/it`, one directory per test, and run with the `run-its` profile. The
  `setup` project installs the parent POM the other projects use, and `pre-integration-tests`
  installs the plugin and a long list of `extraArtifact` plugins into `target/local-repo`, so no
  separate `mvn install` is needed. Run a subset with:

  ```shell
  mvn -B -P run-its verify -Dinvoker.test=setup,jdepend-01
  ```

  `-Dinvoker.test` filters the `setup` project too, so always list it. `jdepend-example` is a
  fixture copy of the plugin sources and has no `verify.groovy`; it only has to build.

- The profile sets `parallelThreads` to 1, overriding the `invoker.parallelThreads` property. The
  `setup` project must finish before any other one starts, so do not raise it (MINVOKER-147).

- Every forked Maven gets `JAVA_TOOL_OPTIONS=-XX:TieredStopAtLevel=1 -XX:+UseSerialGC`.

- The projects run `clean site`; the post-build hook is `verify.groovy`. Write it as `assert`
  statements. Invoker fails a script whose last expression is anything other than `true` or
  `null`, so a trailing `list.each { }` fails the test; use a `for` loop.

- The `extraArtifact` list in the `run-its` profile includes old plugin versions, some of them
  for the integration tests that ran on Maven 2.2.1. Do not update them without running the full
  integration suite.

## Continuous integration

- `.github/workflows/maven-verify.yml` calls the shared `maven-verify.yml@v5` workflow from
  `apache/maven-gh-actions-shared`.
- Branches pushed to this repository build under the `push` event.

## Dependencies

- Dependabot updates are monthly and grouped. Pins in `.github/dependabot.yml`: Maven core
  artifacts stay below 3.10 and `plexus-xml` below 4, because 4.x depends on the Maven 4 XML API.

## Pull requests

- Squash and rebase merges are enabled; merge commits are not. Release Drafter, through the shared
  `apache/maven-gh-actions-shared` workflow, builds the release notes from pull request titles
  and labels, so the title should describe the change for a user.


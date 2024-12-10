# Documentation, download, and usage instructions

Fork of the well-known **[com.github.danielflower.mavenplugins:multi-module-maven-release-plugin](https://danielflower.github.io/multi-module-maven-release-plugin/index.html)** plugin.

Changes to the official plugin:

* Minimal Java version 11
* Supports plugin dependencies
* Prevents NPE when dependency.getLocation() returns null

## Usage

```xml

<plugins>
  <plugin>
    <groupId>io.github.iscmichael.mavenplugins</groupId>
    <artifactId>multi-module-maven-release-plugin</artifactId>
    <version>3.7.0</version>
    <configuration>
      ...
    </configuration>
  </plugin>
</plugins>
```

## How to release

Currently releasing to central Maven repository can only be done manually. We work always on the master branch, so here is a step to step guide:

* Make sure that mvn clean install is running!
* Adapt version in pom.xml: Adapt version, e.g. 3.7.0
* Adapt version TestProject.java: Adapt PLUGIN_VERSION_FOR_TESTS, e.g. 3.7.0
* mvn clean deploy -Prelease
* Upload does not work and blocks, CTRL-C to stop build
* Manually upload the ZIP ../target/central-publishing/central-bundle.zip
    * URL: https://central.sonatype.com/publishing/deployments (you need to login)
    * Deployment name: io.github.iscmichael.mavenplugins:multi-module-maven-release-plugin:{version}
    * File: central-bundle.zip
* In the GUI, the deployment should be valid. If it is valid, click on button "Publish"
* Make a commit with this version. 
```shell
git add --all
git commit -m "Release version 3.7.0"
git tag 3.7.0
git push
git push origin 3.7.0
```
* 
* 
* 


* It takes about 10 minutes

* on master: git tag
* on develop: Adapt version for next snapshot version
* on develop: Adapt version TestProject.java: Adapt PLUGIN_VERSION_FOR_TESTS,


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
    <groupId>io.github.michael-isc.mavenplugins</groupId>
    <artifactId>multi-module-maven-release-plugin</artifactId>
    <version>3.7.0</version>
    <configuration>
      ...
    </configuration>
  </plugin>
</plugins>
```

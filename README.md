![jmtrace](https://socialify.git.ci/StardustDL/jmtrace/image?description=1&font=Bitter&forks=1&issues=1&language=1&owner=1&pulls=1&stargazers=1&theme=Light)

![CI](https://github.com/StardustDL/jmtrace/workflows/CI/badge.svg) ![](https://img.shields.io/github/license/StardustDL/jmtrace.svg)

A java agent that trace all shared memory accesses of the classes in a given Java jar package.

## Build

**Requirements**: Java 11+, Gradle 7.3+

```sh
gradle build
```

The built agent jar is at `build/libs/jmtrace-0.1.jar`.

## Run

```sh
java -javaagent:build/libs/jmtrace-0.1.jar -jar demo.jar
```

We provide a script `jmtrace` which resembled the java command to run jmtrace.

```sh
chmod +x ./jmtrace
./jmtrace -jar <jarfile> ...
```

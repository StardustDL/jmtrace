# jmtrace

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

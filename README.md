# jmtrace

## Build

**Requirements**: Java 11+, Gradle 7.3+

```sh
gradle build
```

The built agent jar is at `build/libs/jmtrace-0.1.jar`.

## Run

We provide a script `jmtrace` which resembled the java command to run jmtrace.

```sh
chmod +x ./jmtrace
./jmtrace -jar <jarfile> ...
```

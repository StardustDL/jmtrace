Task Build {
    Exec { gradle build }
}

Task Demo {
    Set-Location ./demo
    javac -encoding 'UTF-8' Main.java
    if (!(Test-Path demo)) {
        mkdir demo
    }
    Move-Item Main.class ./demo/Main.class -Force
    jar cvfe demo.jar demo.Main demo
    Move-Item demo.jar ../build/libs/demo.jar -Force
    Remove-Item demo -Recurse
    Set-Location ..
}

Task Test {
    Exec { chmod +x ./jmtrace }
    Exec { ./jmtrace -jar ./build/libs/demo.jar }
}

Task Local {
    Exec { wsl -- ./jmtrace -jar ./build/libs/demo.jar }
}

Task try -depends Build, Local
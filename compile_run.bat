@dir /b /a:-d /s | findstr /r /e /c:"\.java" > .javafiles
@javac @.javafiles -Xlint:unchecked
@java %1
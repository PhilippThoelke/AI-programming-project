@dir /b /a:-d /s | findstr /r /e /c:"\.java" > .javafiles
@javac @.javafiles
@java %1
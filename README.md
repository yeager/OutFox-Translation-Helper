Translation Helper for Project OutFox
=====================================

This program compares a source and target language file, to checks for missing keys.

Requirements
------------

*If your computer can run Minecraft Java-edition, it should be fine*  
  
This program is written in Kotlin, to be used on the JVM (Java Virtual Machine).  
JVM runtimes's are available for Windows, Linux and Mac.  
To download a runtime: https://www.java.com/en/download/

Usage
-----

Make sure you have a "correct" language file, that has all the required keys. English should be the most up to date.  
This will be the "source" file. And make sure you have a "target" file. The language you want to check for missing keys.

Download the jar-file from the releases section.  
In a terminal, run it like:  
```
java -jar translation-helper.jar
```
Then just follow the instructions in the program.  
The program will list the missing keys, when comparing the source and target files.

For more detailed explanation on how to use the app; See `MANUAL.md`

Support
-------

If you need help with the program, ask for FrankkieNL in the Project OutFox Discord server.

Development
-----------

This project uses the Gradle build system. I recommend using the IDE, IntelliJ IDEA, which has built-in support.
Use the gradle task fatJar to create a stand-alone runnable jar.
Make sure to update the version in build.gradle.kts.
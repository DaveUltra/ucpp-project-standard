#dot_vcxproj_builder - User Manual

Java 8 is required to run this software.

`dot_vcxproj_builder` can be used to rebuild Visual C++ project files using a `CFG.TXT` file.
The `dot_vcxproj_builder.jar` file should be located outside of your Visual Studio solution(s). With this file, you can create shell scripts (`*.bat` or `*.sh`) to run the jar file for a specific solution. The solution name must be passed as a command argument.

##Solution setup

Here we consider a directory where all solutions are stored in.
To use the project files builder, it must be placed in this directory :

```
- [dir] MiscProjects       (solution)
- [dir] NLP                (also a solution)
- dot_vcxproj_builder.jar  (the jar)
- build_misc.bat           (a batch file running the jar for the "MiscProjects" solution).
- build_nlp.bat
```

build_misc.bat :

```
@echo off
java -jar dot_vcxproj_builder.jar MiscProjects
```

##Project setup

When `dot_vcxproj_builder` is launched correctly with a specified solution, it will provide a command prompt with basic commands, such as `exit / quit`, `help` and `gen`.

The `gen` command is used to rebuild project files for a specific project in the solution. When the command is executed, the program will list all directories found in the solution **which contain a file named `CFG.TXT`.**



After that, everything is setup for usage.
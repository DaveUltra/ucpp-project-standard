# dot_vcxproj_builder - User Manual

Java 8 is required to run this software.

`dot_vcxproj_builder` can be used to rebuild Visual C++ project files using a `CFG.TXT` file.
The `dot_vcxproj_builder.jar` file should be located outside of your Visual Studio solution(s). With this file, you can create shell scripts (`*.bat` or `*.sh`) to run the jar file for a specific solution. The solution name must be passed as a command argument.

## Solution setup

The jar file must be placed directly next to the solution directories containing projects based on the UC++ specification (containing at least a `CFG.TXT` file).
Additionally, shell scripts can be placed next to the jar for each solution directory, so that solution choosing can be done faster.

Example :
```
@echo off
java -jar dot_vcxproj_builder.jar MiscProjects
```
where "MiscProjects" is a solution directory containing, well, projects.

## Starting the program correctly

When starting, a Visual Studio solution directory must be given as a command line argument or, if no argument is specified, the program will request input from the user.

One running instance of the program can manage one solution at a time. If the given solution is valid, the program will display a numbered list of all valid project directories. A project considered "valid" must at least have a file named `CFG.TXT` at its root. This means it's also possible to create Visual C++ projects using only this tool, and by adding them to the solution manually through Visual Studio.

## Project list

The program will display a numbered list of all directories containing a `CFG.TXT` file (regardless of whether its contents are valid or not). Then, the user is prompted for a number from the list. If the user inputs a negative number or an out-of-range one, the program will simply exit without modifying anything.

Specifying a correct project will make the program read the configuration stored in the project's `CFG.TXT`, then rebuild the `*.vcxproj` and `*.vcxproj.filters` files based on the configuration.
If everything went correctly, the program will close automatically and showing the Visual Studio window (if it was already open) will also show a dialog box asking if the user wants to reload the modified project.

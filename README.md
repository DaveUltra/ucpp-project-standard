# UCpp Project Standard (pre-alpha)

Custom C++ Project management standard, used for my own projects.

## Contents

- [Goals](#goals)
- [History](#history)
- [Examples](#examples)

## Goals

The goals of this project is to create a standard for C/C++ projects, by specifying :
- a universal file format for project configuration.
- much more to add to this list...

At its current state, the standard is not a "standard" at all. It is actually very platform-dependent and all the talk in this document is better interpreted as a big brain-storming discussion for this standard.

## History

The idea of a universal project management framework didn't come to my mind immediately. Instead I was just looking for a way to automatically generate Visual Studio project files, so that filters would match directories (I did not like having huge lists which were not broken down in directories, for headers and source files).
That was easy enough, just a few XML builds... but then I realized that I'd lose all infos about wether a project is an executable or a (static / dynamic) library, its include paths, its libraries and library paths... So I had to store that information somewhere.

This somewhere became the infamous `CFG.TXT` (yes, all in caps). The syntax was very simple, although I've reworked it many times. But at least I think I have defined a simple, yet efficient syntax for this file, that suits my needs.

Now, all my active projects have a CFG.TXT at their root, and all Visual Studio solutions have a tool for regenerating project files using the corresponding `CFG.TXT` file.

## Examples

Here is an example of a `CFG.TXT` file for a static library with various OpenGL libraries dependencies :
```
projType lib
pch TestGame/TestGame.h

include ../glfw/include/
include ../some_lib/headers/
```

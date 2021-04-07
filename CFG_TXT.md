# CFG.TXT Specifications

## What is it?

`CFG.TXT` files are what make most of the projects' informations. They define the type of module we want to build (an executable, a static / dynamic library), the include paths, the library paths and libraries to be linked to the module.
These files should be present in the root of projects using the UC++ specification.

## Current syntax & features

Currently, each line in the file represents an entry for a piece of information. There is no strict ordering of these entries, but there is a conventional way to order them.
A line is basically formed of two parts :
```
[data_descriptor] [value]
```
where :
- `data_descriptor` is the type of information on the line.
- `value` is a string containing the data. It can contain any character except line feeds.

A line can be empty, in which case it should be ignored by the parsers.

At the moment, we can define the following data for a project configuration :
- the project type (`projType [exe/lib]`)
- an include path (`include [the_path]`)
- debug and release library paths (`libPathD/libPathR [the_path]`)
- libraries to link (`lib [the_lib_without_extension]`)

## Example

Here is the example of a Linux executable project including a library called "libmystuff.a".
We are assuming that the library's headers and compiled static lib are located next to the project's directory.
```
projType exe

include ../my_stuff_1.0/include/

libPathD ../my_stuff_1.0/lib/debug/
libPathR ../my_stuff_1.0/lib/release/

lib mystuff
```

As you can see, for portability, the "lib" prefix and the extension were stripped away from the library name. This is useful because under Windows, for example, we'd only need to add ".lib" at the end of the name to find the correct file.
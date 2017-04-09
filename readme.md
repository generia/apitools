# apitools - Tools for managing and converting API descriptions

The apitools project provides a collection of tools that help developers to manage and convert API descriptions.
In the center of the tools is the "api" specification that allows for modeling packages, classes, methods and data types.
The tools focus on reading, editing, converting and writing this "api" specification into other representations.

The tools provide

- **api content-type**: a simple humanly readable XML format using MIME type "model/api+xml"
- **api Java model**: a simple Java model of pojos that can be used standalone without any dependencies
- **Eclipse Api Editor**: an eclipse plugin providing an editor for the "api" content-type with suport form UML conversion

The "api" specification is derived from the Ecore model of the [Eclipse Modeling Framework](http://www.eclipse.org/modeling/emf/). It allows for using the expressive power of the Ecore model in non Eclipse runtime environments.

The apitools project has the following sub-projects:

1. **model**: standalone Java pojos describing the "api" specifiation
2. **eclipse**: Eclipse editor for "api" descriptions.

## '[model](model)' sub-project

A standalone Java pojos describing the "api" specifiation.

## '[editor](editor)' sub-project

A collections of Eclipse plugins that provide 

- an editor for reading, changing and writing "api" descriptions
- UML converter to transform "api" descriptions to UML models
- UML profile builder to create UML profiles used for annotation mappings



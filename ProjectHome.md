# Maven Deltacoverage Plugin #

## Introduction ##

Delta coverage plugin is used to find code coverage only for code changed since a specific version. It uses xml coverage report from cobertura to get the current coverage, it then uses information from SCM repository to find changes to code since specified version/tag. Based on this information, it calculates delta code coverage. Finally it generates output in HTML and XML format. The XML format is same as the the cobertura xml coverage report. Currently only supports Cobertura code coverage report and CVS scm repository.

## Documentation ##

Refer to the [maven plugin documentation](https://maven-deltacoverage-plugin.googlecode.com/git/documentation/index.html) for more info on usage and configuration of the plugin.


---

_Refer to the [parent project](https://code.google.com/p/deltacoverage/) for more related information._
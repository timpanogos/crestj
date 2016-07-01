Interface Repository MySQL
====
***

The Interface Repository MySQL Library implements the [Interface Repository persistence API](../interface-repository-data-accessor/README.md)
in the Data Accessor Library using a MySQL database.

## Build Instructions
This project uses [Apache Ant](http://ant.apache.org/) and [Apache Ivy](http://ant.apache.org/ivy/) to build and resolve dependencies. In addition, 
a Java Development Kit (JDK) of 1.7 or greater is required.

To build, make sure any dependencies are available in a configured Ivy repository, and execute 'ant dist' in the root of the project (this is the 
default ant target). To publish the build to your local ivy repository (and make it available for other builds to depend on), execute 'ant publish'.

If you plan to make a complete distribution including source, you will also need to use the 'dist-src' and 'publish-src' targets.
Typically, these would be executed prior to the binary build ('dist' and 'publish' targets), since they require a clean directory.

##Runtime Prerequisites
A Java Runtime Environment (JRE) of 1.7 or greater is required.

##How to Report an Issue
Issues can be reported through the issue tracking system of the OpenDOF Project at https://issue.opendof.org

Information on the process for reporting an issue can be found at https://opendof.org/report-an-issue.
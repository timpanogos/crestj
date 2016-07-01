Interface Repository Servlet
====
***

The Interface Repository Servlet Library implements a Java servlet, providing web access to the Interface Repository.

The Servlet accepts HTTP requests on the /interface path with a query string containing the following fields:

* iid - Uniquely identifies an interface within a repository. Two formats are supported for OpenDOF interfaces. A URL encoded interface identifier 
in OpenDOF Standard Form and 'dof/x.y' where x is the registry and y is the hex identifier.
* repo - Specifies the repository to which the interface belongs.
* user - The submitter of the interface.
* group - The group or organization that the submitter belongs to.
* trans - Specify the translation of the interface definition(e.g. raw, html, java)
* publish - Whether or not the interface is published.
* version - The version of the interface.
* cmd - Supported commands are GetInterface, AddWorking, Update, Publish, ListAll.


## Build Instructions
This project uses [Apache Ant](http://ant.apache.org/) and [Apache Ivy](http://ant.apache.org/ivy/) to build and resolve dependencies. In addition, 
a Java Development Kit (JDK) of 1.7 or greater is required.

To build, make sure any dependencies are available in a configured Ivy repository, and execute 'ant dist' in the root of the project (this is the 
default ant target). To publish the build to your local ivy repository (and make it available for other builds to depend on), execute 'ant publish'.

If you plan to make a complete distribution including source, you will also need to use the 'dist-src' and 'publish-src' targets.
Typically, these would be executed prior to the binary build ('dist' and 'publish' targets), since they require a clean directory.

##Runtime Prerequisites
A Java Runtime Environment (JRE) of 1.7 or greater is required. A Java servlet container and web server such as Tomcat is also required.

##How to Report an Issue
Issues can be reported through the issue tracking system of the OpenDOF Project at https://issue.opendof.org

Information on the process for reporting an issue can be found at https://opendof.org/report-an-issue.
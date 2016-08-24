# Interface Repository Installation Guide #

This version of the tools-interface-repository comes with Apache Tomcat WEB server configuration templates.
This guide will cover there use.  

This version also comes with the interface-repository-mysql component which is a MySQL implementation of the 
interface-repository-data-accessor component's `DataAccessor` interface.  The MySQL database setup and population
via the interface-repository-cli's `InterfaceRepositoryManage` application is covered here.

## Tomcat WEB Server setup ##
The supplied Tomcat configuration file templates are based on version 8.0.  They can be found in the interface-repository-web 
component under the tomcat folder.  The file structure under interface-repository-web/tomcat assumes a default Linux installation,
adjust as needed per your actual ${catalina.base} installation.  

### Install Tomcat ###
First Install Tomcat per instructions found at https://tomcat.apache.org/tomcat-8.0-doc/index.html .

#### Tomcat DataSource setup ####
Setup a Tomcat MySQL Datasource by modifying your ${catalina.base}/conf/context.xml with the contents of the 
interface-repository-web/tomcat/etc/tomcat8/context.xml file. The following is a snippet of the context.xml file:

    <Resource name="jdbc/interfacerepository" auth="Container" type="javax.sql.DataSource"
    maxTotal="100" maxIdle="30" maxWait="10000" username="YOUR-USER"
    password="YOUR-USER-PASS" driverClassName="com.mysql.jdbc.Driver"
    url="jdbc:mysql://DB-URL:3306/DB-NAME?characterEncoding=UTF-8"/>
 
The name property must match the "opendof.tools.interface.repository.persist.datasource-jndi-name" keyed name given in the interface-repository.properties.
The DB-NAME portion of the url property must match the "opendof.tools.interface.repository.persist.mysql.db-name" keyed name given in the interface-repository.properties file.
This properties file is described below.

The maxTotal, maxIdle and maxWait properties can be modified to your requirements. The username, password and url 
properties will need to be modified with valid values. 

You will need to download and copy the following two jars into a folder which will be in Tomcat's java classpath.  
For example the /usr/share/java/tomcat8 folder on Linux installations.

* mysql-connector-java-5.1.35.jar or newer 
* tomcat-dbcp-8.0.23 or newer

#### Interface-Repository servlet setup ####
Use Tomcat's manager web application or other Tomcat API's to deploy the interface-repository-web.1.0.war file to
your Tomcat server.  This will provide the Tomcat WebContent for the interface-repository-servlet. You may need to rename the 
.war file to the desired application name(e.g. interface-repository.war) if Tomcat is configured to use the .war filename as the 
application name.

##### interface-repository-properties #####
An example interface-repository-properties file is provided in the interface-repository-web components 
tomcat/usr/share/java/tomcat8/interface-repository.properties file.  A modified version of this file will need to exist in a
Tomcat java classpath folder.  The following is the content of this example properties file:

    # Datasource configuration
    opendof.tools.interface.repository.persist.class=org.opendof.tools.repository.interfaces.mysql.MysqlDataAccessor

    # MysqlDataAccessor configuration.  Commented key/value pairs reflect default values and are not required.
    opendof.tools.interface.repository.persist.mysql.user=DB-USER
    opendof.tools.interface.repository.persist.mysql.password=DB-USER-PASS
	opendof.tools.interface.repository.persist.mysql.db-name=DB-NAME

    #opendof.tools.interface.repository.persist.mysql.source-name=interface-repository
    #opendof.tools.interface.repository.persist.mysql.server-name=interface-repository-db-server
    #opendof.tools.interface.repository.persist.mysql.port=3306
    #opendof.tools.interface.repository.persist.mysql.max-conns=20
	#opendof.tools.interface.repository.persist.mysql.maxActive=20
	#opendof.tools.interface.repository.persist.mysql.maxIdle=5
    #opendof.tools.interface.repository.persist.datasource-jndi-name=jdbc/interfacerepository

    # Repository Type Controllers
    # the second parameter must match what the browser uses for its "repo" url command parameter
    opendof.tools.interface.repository.controller0=org.opendof.tools.repository.interfaces.opendof.OpendofController opendof
    opendof.tools.interface.repository.controller1=org.opendof.tools.repository.interfaces.allseen.AllseenController allseen

    # If running server side and using the tomcat configuration, these two are the defaults

	opendof.tools.log-file-path=/var/log/tomcat8/interface-repository.log
	opendof.tools.interface.repository.keep8080=true
    
As noted in the first comment of the file, any key/value pairs that are commented out, the value reflects the default value
assigned to the key if not specified.

The ...mysql.jndi-name and ...mysql.db-name must match the values used in Tomcat's global context.xml data source setup 
described above.  The ...mysql.jndi-name must also match the datasource reference in the web.xml file.

The ...persist.class is only changed if another implementation of the interface-repository-data-accessor is desired.  
For this MySQL implementation, the ...mysql.user, ...mysql.password, and ...mysql.db-name must be modified with a valid user/pass that 
has access to read/modify/write the declared datasource/database from ...mysql-db-name property.

The ...repository.controller# keys declare custom repository controllers (interface-repository-core component extensions) 
and their associated repotype.  This "repo" value is used as base structure/file names in the WebContent structure as well
as the client browser side URL commands "repo" parameter.  The current OpenDOF and Allseen implementations require the values
shown.

The ...repository.keep8080 key defaults to false, indicating the the Tomcat server is likely running on a DNS and using 
port 80.  If your Tomcat installation is using port 8080, this must be set to true.
   
##### web.xml #####
The web.xml file found in the interface-repository-web components WebContent/WEB-INF folder will require modification if
the default value of the following property key is not used.

* opendof.tools.interface.repository.persist.datasource-jndi-name=jdbc/interfacerepository

##### WebContent structure #####
Modifications in this section is only required if extending the current implementation with new repository controllers or
modifying the default implementation.

The .../catalog structure supports Apache XML cataloging (see http://xerces.apache.org/xerces2-j/faq-xcatalogs.html).

The .../catalog/opendof structure is custom to the interface-repository-opendof component's referenced XML schema .xsd 
files which are located here.  .../catalog/opendof/opendof.xml is the oasis catalog mapping file for the required opendof
controllers schemas.

Other custom controllers (i.e. interface-repository-allseen) can add their own catalogs by following the same pattern as opendof.
For example:
    .../catalog/allseen.xml
    .../catalog/allseen/*.xsd
    
The base names used (i.e. opendof, allseen) must match the repotype's configured in the interface-repository.properities 
file as explained above.

The .../images folder is generic and images can be added here as needed by translation extensions, customized web pages etc.

The .../translation structure is custom to the interface-repository-opendof and interface-repository-allseen components.
Each child folder names must match the repotypes configured in the interface-repository.properties file as explained above.

New repositories and XSL translations can be added by creating the appropriately named folder and dropping in .xsl files.
The interface-repository-servlet component supports a URL "trans" parameter.  The value of this parameter is used as the 
base (the prefix appends '-' to the base) name of the desired .xsl translation.  Currently supported translations are
<ul>
<li>trans=html (html-*.xsl is used)</li>
<li>trans=java (html-*.xsl is used)</li>
</ul>
This allows for dynamic extensibility of display translations by adding new URL trans values to client side code and supplying 
the corresponding directory/.xsl structure on the server.  

## MySQL Database setup ##

The tables.sql scripts found in the interface-repository-mysql component's schema folder can be used to install the Interface 
Repositories schema in an existing MySQL database.    

 
### InterfaceRepositoryManage Application ###

The InterfaceRepositoryManage application of the interface-repository-cli component can be used to manage the database.
#!/bin/bash
initctl stop tomcat
rm -rf  /opt/tomcat/webapps/literature*
rm -rf /opt/tomcat/work/Catalina/localhost/literature*
cp /home/lerkasan/workspace/literatureProj/target/literature-1.0.0.war /opt/tomcat/webapps/literature.war
initctl start tomcat

B1;3409;0c#!/bin/bash
service=apache-tomcat-8.0.28

file1=/var/lib/buildkite-agent/builds/ubuntu-vm-1/asu/ssproject/SoftwareSecurity/target/cse-1.0.0-BUILD-SNAPSHOT.war
file2=~/Install/apache-tomcat-8.0.28/webapps/cse.war

if [[ $file1 -nt $file2 ]];
then
    ~/Install/apache-tomcat-8.0.28/bin/shutdown.sh
    cp $file1 $file2
fi
if (( $(ps -ef | grep -v grep | grep $service | wc -l) > 0 ))
then
echo "$service is running!!!"
else
~/Install/apache-tomcat-8.0.28/bin/startup.sh
fi

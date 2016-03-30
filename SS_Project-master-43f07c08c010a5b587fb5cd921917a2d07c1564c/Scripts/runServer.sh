#!/bin/sh
#set -x
echo "Compiling Maven"
GIT_PATH=~/College/SS_Project/RemoteProject/SS_Project
PROJ_PATH=$GIT_PATH/SoftwareSecurity
cd $PROJ_PATH
git pull --rebase
mvn clean install
APACHEDIR=$GIT_PATH/Scripts/apache-tomcat-8.0.26/
if [ ! -d "$APACHEDIR" ]; then
    mkdir $GIT_PATH/Scripts
    tar -xvzf $GIT_PATH/Scripts/apache-tomcat-8.0.26.tar.gz -C $GIT_PATH/Scripts
else
$GIT_PATH/Scripts/apache-tomcat-8.0.26/bin/shutdown.sh
fi
cp $PROJ_PATH/target/cse-1.0.0-BUILD-SNAPSHOT.war $GIT_PATH/Scripts/apache-tomcat-8.0.26/webapps/cse.war
$GIT_PATH/Scripts/apache-tomcat-8.0.26/bin/startup.sh
echo "Server Running on Port 8080"


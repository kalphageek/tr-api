#!/bin/bash

#Set at jenkins executor
appName=$1

cd ~/workspace/
DIR=$(ls | grep $appName | head -n 1)
cd $DIR
JAR_NAME=$(ls target/*.jar | tail -n 1)
echo "> jar name : $JAR_NAME"
echo "> checking pid on the running process"
CURRENT_PID=$(ps -ef | grep jar | grep $appName | awk '{print $2}')
if [ -z $CURRENT_PID ];
then
  echo "> there is no running process."
else
  echo "> kill -9 $CURRENT_PID"
  kill -9 $CURRENT_PID sleep 10
fi
echo "> deploy new version."
nohup java -jar $JAR_NAME &
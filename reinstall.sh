#!/bin/bash

set -e -u

# You may need to set the JDK to Version 8 or 11. 
# export JAVA_HOME=`/usr/libexec/java_home -v 11`

echo "Running with Confluent Platform located in $CONFLUENT_HOME"

(cd connector && mvn clean package)
confluent local services connect stop
cp connector/target/http-sink-sample-jar-with-dependencies.jar $CONFLUENT_HOME/share/java
confluent local services connect start

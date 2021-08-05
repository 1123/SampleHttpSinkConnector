#!/bin/bash

set -e -u

mvn clean package
confluent local services connect stop
cp target/http-sink-sample.jar $CONFLUENT_HOME/share/java
confluent local services connect start

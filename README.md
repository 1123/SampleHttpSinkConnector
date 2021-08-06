# Custom HTTP Sink with Dead Letter Functionality 

This is a POC to demonstrate how to export data from Kafka to a REST Service via a custom Kafka Connect Sink connector and use the Kafka Connect ErrantRecordReporter to send bad messages to a Dead Letter topic. 

This is a small footprint connector that can easily be adjusted for additional requirements. 

A production ready HTTP Sink Connector by Confluent is available on Confluent Hub: https://www.confluent.io/hub/confluentinc/kafka-connect-http


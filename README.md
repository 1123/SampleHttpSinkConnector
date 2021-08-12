# Custom HTTP Sink with Dead Letter Functionality 

This is a POC to demonstrate how to export data from Kafka to a REST Service via a custom Kafka Connect Sink connector and use the Kafka Connect ErrantRecordReporter to send bad messages to a Dead Letter topic. 

This is a small footprint connector that can easily be adjusted for additional requirements. 

A production ready HTTP Sink Connector by Confluent is available on Confluent Hub: https://www.confluent.io/hub/confluentinc/kafka-connect-http

## Running the Demo

The demo will bring up Confluent Platform and a REST service acting as a sink system. 
The REST service will accept messages with odd message keys, but not with even message keys. 
This is to test the dead letter functionality of the sink connector. 
So don't be surprised to see exceptions in the log of the REST service. 
Subsequently we will deploy the connector to the Apache Connect cluster that comes with Confluent Platform, and produce some sample data to the input topic of the connector. 
Finally we will inspect the logs of the connect service to see that the messages with odd keys were processed, and check the contents of the dead-lettter-topic to find the messages with even keys. 

### Prerequisites

* JKD 8 or JDK 11 installed
* A recent version of Confluent Platform installed
* Maven for building the project
* a Linux shell to execute the scripts. For Windows systems the scripts would need to be adapted. 
* the Apache Kafka command line utilities need to be on the path. 

### Steps

* start Confluent Platform: `confluent local services start`. 
* start the REST service, which is acting as the sink system: `cd rest-service; mvn spring-boot:run`. 
  Keep this terminal open, such that you can see the logs, when messages are written to the service. 
  Carry out the following steps in a different terminal window. 
* build and install the connector: `./reinstall.sh`. 
  The `CONFLUENT_HOME` environment variable must be set such that the script knows where to put the connector jar archive. 
* deploy the connector: `./deploy-connector.sh connector.json`
* check the connector status: `./get-connector-status.sh http-sink-sample` . 
  This should show the connector in running state. 
* Produce some sample data: `./produce-sample-data.sh`. Inspect the logs of the REST service to see that messages arrive, some of them being processed successfully, some of them causing the rest service to return HTTP 500 messages. 
* Check that the connector forwarded the bad messages to the dead-letter-topic: `print-dead-letter-topic-messages.sh`


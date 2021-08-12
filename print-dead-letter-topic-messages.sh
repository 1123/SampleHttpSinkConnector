#!/bin/bash

kafka-console-consumer \
  --bootstrap-server localhost:9092  \
  --topic http-sink-dlt \
  --property print.key=true  \
  --from-beginning

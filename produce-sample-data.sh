for i in {1..10}; do
  DATE=$(date)
  echo "$i;$DATE" | kafka-console-producer --bootstrap-server localhost:9092 --topic input-topic --property "parse.key=true" --property "key.separator=;"
done

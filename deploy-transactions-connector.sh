curl localhost:8083/connectors \
  -H  "Content-Type:application/json" \
  -X POST \
  -d @transactions-sink.json

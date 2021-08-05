set -u -e

curl \
  -X POST \
  -H "Content-Type: application/json"  \
  -H "Accept: application/json" \
  -d @$1 \
  localhost:8083/connectors

#!/bin/bash

set -u -e

. docker.env

STATEMENT="select * from tables"

java -jar ./ngdbc-2.7.11.jar \
  -u $USER,$PASS \
  -n $URL \
  -o encrypt=True \
  -o validatecertificate=false \
  -c "$STATEMENT"


#!/bin/bash

set -u -e

curl localhost:8083/connectors/$1 -X DELETE 

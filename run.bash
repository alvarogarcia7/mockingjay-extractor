#!/bin/bash

set -euf -o pipefail

java -jar ./*.jar \
  $1 \
  $2 \
  "https://$REAL_URL.example.net"

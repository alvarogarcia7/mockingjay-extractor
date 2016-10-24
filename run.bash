#!/bin/bash -e

java -jar ./*.jar \
  $1 \
  $2 \
  "https://$REAL_URL.example.net"

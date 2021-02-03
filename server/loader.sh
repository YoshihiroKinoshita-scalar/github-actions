#!/bin/bash
java -jar $SCHEMATOOL/scalar-schema-standalone-2.4.1.jar --cassandra -h localhost -u cassandra -p cassandra -f schema/demo-user.json -R 1

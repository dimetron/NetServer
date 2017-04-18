#!/bin/sh

#stop and cleanup
docker-compose down
docker-compose rm -vf
touch docker-compose.yml
rm -rf ./volumes/scylladb/data/

#build app container using sbt
sbt docker:publishLocal
docker-compose up -d

sleep 10
#wait 10 sec and restart akka cluster
docker-compose restart  akka1 akka2 akka3 akka4

#run tests
sbt gatling-it:test
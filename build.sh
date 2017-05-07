#!/bin/sh

#stop and cleanup
docker-compose down
docker-compose rm -vf
touch docker-compose.yml
rm -rf ./volumes/cassandra/data/

#build app container using sbt
sbt -J-Xms64m -J-Xmx256m -J-XX:ReservedCodeCacheSize=128m -batch docker:publishLocal
docker-compose up -d database
sleep 30
docker-compose logs database

#wait 10 sec and restart akka cluster

sleep 10 && docker-compose up -d akka1
sleep 10 && docker-compose up -d akka2
sleep 10 && docker-compose up -d akka3

docker-compose up -d

docker-compose logs akka1
docker-compose logs akka2
docker-compose logs akka3

#run tests
echo "sbt -J-Xms512m -J-Xmx1024m -J-XX:ReservedCodeCacheSize=128m -batch gatling-it:test"
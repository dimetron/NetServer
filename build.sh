#!/bin/sh
sbt docker:publishLocal

docker-compose rm -vf
docker-compose up

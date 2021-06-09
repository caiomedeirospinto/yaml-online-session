#!/bin/bash

export DATABASE_HOST=localhost
export DATABASE_NAME=yaml_online
export DATABASE_USER=yaml
export DATABASE_PASS=yaml

if [ ! "$(docker ps -a | grep mariadb)" ]; then
  docker run --name=mariadb -d -p 3306:3306 \
    -e MARIADB_RANDOM_ROOT_PASSWORD="true" \
    -e MARIADB_DATABASE="$DATABASE_NAME" \
    -e MARIADB_USER="$DATABASE_USER" \
    -e MARIADB_PASSWORD="$DATABASE_PASS" mariadb:latest;
else
  docker start mariadb;
fi

./mvnw compile quarkus:dev
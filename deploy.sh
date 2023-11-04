#!/bin/bash
if docker ps --format "{{.Names}}" | grep -q "redis";
then
  # redis 컨테이너가 실행 중이면 server만 재실행
  echo "Redis container is already running."
  docker-compose.yml docker-compose -f "docker-compose.yml" up -d --build server
else
  # redis 컨테이너가 실행 중이 아니면 모두 재실행
  docker-compose.yml docker-compose -f "docker-compose.yml" up -d --build
fi

#!/bin/bash
if docker ps --format "{{.Names}}" | grep -q "redis";
then
  # redis 컨테이너가 실행 중이면 server만 재실행
  echo "Redis container is already running."
  docker-compose stop springboot
  docker-compose -f "docker-compose.yml" up -d --build springboot
else
  # redis 컨테이너가 실행 중이 아니면 모두 재실행
  echo "Redis container is not running."
  docker-compose -f "docker-compose.yml" up -d --build
fi

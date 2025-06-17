#!/bin/zsh
echo "------ Starting RabbitMQ ------"
docker run -d --hostname bunny_host \
    --name bunny_name \
    -p 8100:15672 \
    -p 5672:5672 \
    --network dev_net \
    rabbitmq:3-management



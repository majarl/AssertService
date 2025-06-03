#!/bin/bash

echo "----- Starting nginx -----"
PORT=80
docker run -it -d -p$PORT:80 --name nginx-server nginx-server

echo "... Done starting."
docker ps | grep nginx

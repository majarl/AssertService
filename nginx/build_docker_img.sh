#!/bin/bash

echo "----- Building nginx image -----"
docker build -f Dockerfile -t nginx-server .

echo
echo "... Done building nginx image."

docker images | grep nginx-server

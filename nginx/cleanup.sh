#!/bin/bash

echo "--> Stopping nginx-server..."
docker stop nginx-server

echo "--> Removing container..."
docker rm nginx-server



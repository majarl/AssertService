#!/bin/bash
docker run --mount type=bind,src=./upload_dir,dst=/mnt/media_upload_dir \
  -d --name media-uploader \
  -p 8080:8080 media-uploader

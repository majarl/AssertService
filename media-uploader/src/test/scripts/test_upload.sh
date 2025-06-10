#!/bin/zsh
echo "Attempting to post test_file.txt ..."
http -f POST http://localhost:8080/api/media-uploader/upload file@test_file.txt
# curl -F "file=@test_file.txt" http://localhost:8080/api/media-uploader/upload
echo "... done"
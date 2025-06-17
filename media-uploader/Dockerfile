FROM eclipse-temurin:21
RUN mkdir /opt/app
# RUN mkdir /mnt/media_upload_dir

COPY target/*.jar /opt/app/media-uploader.jar

WORKDIR /opt/app

ENTRYPOINT ["java", "-jar", "media-uploader.jar"]
EXPOSE 8080


FROM alpine:latest

RUN apk update
RUN apk add bash
RUN apk add openjdk21
RUN apk add ffmpeg

RUN mkdir /opt/app
COPY target/*.jar /opt/app/media-converter.jar

WORKDIR /opt/app

ENTRYPOINT ["java", "-jar", "media-converter.jar"]
EXPOSE 8080
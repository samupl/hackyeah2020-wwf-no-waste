FROM openjdk:11

RUN mkdir /app
RUN mkdir /app/images
WORKDIR /app

ADD . /app
ADD docker/cfg/application.yml src/main/resources/

RUN ./gradlew build

ENTRYPOINT ["/app/gradlew", "bootRun"]

FROM openjdk:11

RUN mkdir /app
WORKDIR /app

ADD . /app
ADD docker/cfg/application.yml src/main/resources/

RUN ./gradlew build

ENTRYPOINT ["/app/gradlew", "bootRun"]

FROM maven:3.9.4 as build

COPY . /app/

WORKDIR /app

RUN mvn package

FROM openjdk:21-jdk-slim-bullseye

WORKDIR /app

COPY --from=build /app/target/*.jar ./app.jar

RUN useradd -s /bin/bash java

RUN chown -R java /app/

USER java

EXPOSE 8000

CMD java -jar app.jar
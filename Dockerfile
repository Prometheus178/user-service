FROM openjdk:17-jdk-slim

ENV HOME /home
WORKDIR /home/app
RUN mkdir /home/jenkins

ARG JAR_FILE=user-service-3.2.jar
ARG JAR_FILE_SOURCE=build/libs/${JAR_FILE}

COPY ${JAR_FILE_SOURCE}  ${JAR_FILE}

ENTRYPOINT ["java","-jar","user-service-3.2.jar"]
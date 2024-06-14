FROM openjdk:17-jdk-alpine
MAINTAINER afreely
COPY build/libs/avios-calculator-0.0.1-SNAPSHOT.jar avios-calculator-1.0.0.jar
ENTRYPOINT ["java","-jar","/avios-calculator-1.0.0.jar"]
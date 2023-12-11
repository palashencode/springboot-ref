FROM amazoncorretto:21-alpine3.
MAINTAINER example.com
VOLUME /tmp
COPY libs/springboot-0.0.1-plain.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
FROM amazoncorretto:11
LABEL authors="송진현"
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
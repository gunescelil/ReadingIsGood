FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/ReadingIsGood-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://mongocontainer/test", "-jar","/app.jar"]
EXPOSE 8050

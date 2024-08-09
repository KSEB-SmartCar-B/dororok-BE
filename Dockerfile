FROM openjdk:17-jdk

VOLUME /tmp

ARG JAR_FILE=./build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -Dspring.profiles.active=${PROFILE} -jar /app.jar"]
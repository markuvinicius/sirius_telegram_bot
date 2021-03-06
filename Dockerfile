FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
ENV BOT_USER_NAME=<YOUR_BOT_USER_NAME>
ENV TELEGRAM_TOKEN=<YOUR_TELEGRAM_TOKEN>
ENV WORDS_API_KEY=<YOUR_API_KEY>
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
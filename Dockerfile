FROM gradle:8.2-jdk AS TEMP_BUILD_IMAGE

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY build.gradle.kts settings.gradle.kts $APP_HOME
COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/wrapper
COPY . .

RUN ./gradlew clean build  \
    --no-daemon  \
    --parallel \
    --exclude-task test  \
    --exclude-task :checkstyleMain  \
    --exclude-task :checkstyleTest \
    --exclude-task :check \
    --exclude-task :testClasses

FROM bellsoft/liberica-openjdk-debian:latest

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

ENV ARTIFACT_NAME=analyzer-20-all.jar
ENV APP_PORT=8080
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
COPY src/main/resources/schema.sql .
COPY src/main/resources/templates ./templates

EXPOSE $APP_PORT
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}
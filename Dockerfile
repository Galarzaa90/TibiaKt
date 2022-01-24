FROM adoptopenjdk/openjdk16:debianslim-jre as builder
COPY *.gradle.kts gradle.* gradlew /home/gradle/app/
COPY gradle/ /home/gradle/app/gradle/
COPY buildSrc/ /home/gradle/app/buildSrc/
WORKDIR /home/gradle/app

# This step will fail because source is still not there, but at least dependencies will be downloaded
RUN ./gradlew build -x check --parallel --continue > /dev/null 2>&1 || true

COPY . /home/gradle/app
RUN ./gradlew build -x test shadowJar --parallel

EXPOSE 8080

LABEL maintainer="Allan Galarza <allan.galarza@gmail.com>"
LABEL org.opencontainers.image.licenses="Apache 2.0"
LABEL org.opencontainers.image.authors="Allan Galarza <allan.galarza@gmail.com>"
LABEL org.opencontainers.image.url="https://github.com/Galarzaa90/tibiakt"
LABEL org.opencontainers.image.source="https://github.com/Galarzaa90/tibiakt"
LABEL org.opencontainers.image.vendor="Allan Galarza <allan.galarza@gmail.com>"
LABEL org.opencontainers.image.title="TibiaKT"
LABEL org.opencontainers.image.description="API that parses website content into Kotlin data."

FROM adoptopenjdk/openjdk16:debianslim-jre
COPY --from=builder ./home/gradle/app/tibiakt-server/build/libs/tibiatk-server.jar .
CMD [ "java", "-jar",  "./tibiatk-server.jar" ]

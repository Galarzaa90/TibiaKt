FROM amazoncorretto:19 AS builder
COPY *.gradle.kts gradle.* gradlew /app/
COPY gradle/ /app/gradle/
COPY buildSrc/ /app/buildSrc/
WORKDIR /app

# This step will fail because source is still not there, but at least dependencies will be downloaded
RUN ./gradlew build -x check -x detekt --parallel --continue > /dev/null 2>&1 || true

COPY . /app
RUN ./gradlew build -x test -x detekt shadowJar --parallel

FROM amazoncorretto:19
COPY --from=builder ./app/tibiakt-server/build/libs/tibiatk-server.jar .

EXPOSE 8080

LABEL maintainer="Allan Galarza <allan.galarza@gmail.com>"
LABEL org.opencontainers.image.licenses="Apache 2.0"
LABEL org.opencontainers.image.authors="Allan Galarza <allan.galarza@gmail.com>"
LABEL org.opencontainers.image.url="https://github.com/Galarzaa90/tibiakt"
LABEL org.opencontainers.image.source="https://github.com/Galarzaa90/tibiakt"
LABEL org.opencontainers.image.vendor="Allan Galarza <allan.galarza@gmail.com>"
LABEL org.opencontainers.image.title="TibiaKT"
LABEL org.opencontainers.image.description="API that parses website content into Kotlin data."

CMD [ "java", "-jar",  "./tibiatk-server.jar" ]

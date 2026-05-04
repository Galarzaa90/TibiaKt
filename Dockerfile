# syntax=docker/dockerfile:1.7
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Keep Gradle caches out of the layer stack
ENV GRADLE_USER_HOME=/gradle
# Stop Gradle from trying to download extra JDKs in CI
ENV ORG_GRADLE_JAVA_INSTALLATIONS_AUTO_DOWNLOAD=false

COPY gradlew ./
COPY gradle/ ./gradle/
COPY settings.gradle.kts ./
COPY build.gradle.kts ./
COPY gradle.properties ./
COPY gradle/libs.versions.toml ./gradle/libs.versions.toml
COPY build-logic/ ./build-logic/
RUN mkdir -p dokka tibiakt-core tibiakt-client tibiakt-server
# Warm Gradle/plugin caches deterministically before copying full sources.
RUN --mount=type=cache,target=/gradle ./gradlew help --no-daemon

COPY tibiakt-core/ /app/tibiakt-core
COPY tibiakt-client/ /app/tibiakt-client
COPY tibiakt-server/ /app/tibiakt-server
COPY dokka/ /app/dokka

ARG VERSION=0.0.0-SNAPSHOT
RUN --mount=type=cache,target=/gradle ./gradlew -PVERSION=${VERSION} build -x test -x detekt tibiakt-server:shadowJar --parallel --no-daemon

FROM eclipse-temurin:21-jre-alpine
COPY --from=builder ./app/tibiakt-server/build/libs/tibiatk-server-shadow.jar .

EXPOSE 8080

LABEL maintainer="Allan Galarza <allan.galarza@gmail.com>"
LABEL org.opencontainers.image.licenses="Apache 2.0"
LABEL org.opencontainers.image.authors="Allan Galarza <allan.galarza@gmail.com>"
LABEL org.opencontainers.image.url="https://github.com/Galarzaa90/tibiakt"
LABEL org.opencontainers.image.source="https://github.com/Galarzaa90/tibiakt"
LABEL org.opencontainers.image.vendor="Allan Galarza <allan.galarza@gmail.com>"
LABEL org.opencontainers.image.title="TibiaKT"
LABEL org.opencontainers.image.description="HTTP API that parses content from Tibia.com into JSON data."


HEALTHCHECK --interval=30s --timeout=60s --start-period=5s --retries=5 \
  CMD wget http://localhost:8080/healthcheck -q -O - > /dev/null 2>&1

ENTRYPOINT [ "java", "-jar",  "./tibiatk-server-shadow.jar" ]

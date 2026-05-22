# syntax=docker/dockerfile:1.7
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Keep Gradle caches out of the layer stack
ENV GRADLE_USER_HOME=/gradle
# Stop Gradle from trying to download extra JDKs in CI
ENV ORG_GRADLE_JAVA_INSTALLATIONS_AUTO_DOWNLOAD=false

COPY --link gradlew ./
COPY --link gradle/ ./gradle/
COPY --link settings.gradle.kts ./
COPY --link build.gradle.kts ./
COPY --link gradle.properties ./
COPY --link gradle/libs.versions.toml ./gradle/libs.versions.toml
COPY --link build-logic/ ./build-logic/
RUN mkdir -p dokka tibiakt-core tibiakt-client tibiakt-server
# Warm Gradle/plugin caches deterministically before copying full sources.
RUN --mount=type=cache,target=/gradle ./gradlew help --no-daemon

COPY --link tibiakt-core/ /app/tibiakt-core
COPY --link tibiakt-client/ /app/tibiakt-client
COPY --link tibiakt-server/ /app/tibiakt-server
COPY --link dokka/ /app/dokka

ARG VERSION=0.0.0-SNAPSHOT
RUN --mount=type=cache,target=/gradle ./gradlew -PVERSION=${VERSION} tibiakt-server:shadowJar -x test -x detekt --parallel --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S tibiakt && adduser -S -G tibiakt tibiakt
COPY --from=builder /app/tibiakt-server/build/libs/tibiatk-server-shadow.jar /app/tibiatk-server-shadow.jar
USER tibiakt

EXPOSE 8080

LABEL maintainer="Allan Galarza <contact@galarzaa.com>"
LABEL org.opencontainers.image.licenses="Apache 2.0"
LABEL org.opencontainers.image.authors="Allan Galarza <contact@galarzaa.com>"
LABEL org.opencontainers.image.url="https://github.com/Galarzaa90/tibiakt"
LABEL org.opencontainers.image.source="https://github.com/Galarzaa90/tibiakt"
LABEL org.opencontainers.image.vendor="Allan Galarza <contact@galarzaa.com>"
LABEL org.opencontainers.image.title="TibiaKT"
LABEL org.opencontainers.image.description="HTTP API that parses content from Tibia.com into JSON data."


HEALTHCHECK --interval=30s --timeout=60s --start-period=5s --retries=5 \
  CMD wget http://localhost:8080/healthcheck -q -O - > /dev/null 2>&1

ENTRYPOINT [ "java", "-jar", "/app/tibiatk-server-shadow.jar" ]

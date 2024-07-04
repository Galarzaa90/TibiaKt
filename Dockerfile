FROM eclipse-temurin:19-alpine AS builder
COPY *.gradle.kts gradle.* gradlew /app/
COPY gradle/ /app/gradle/
COPY buildSrc/ /app/buildSrc/
WORKDIR /app

# This step will fail because source is still not there, but at least dependencies will be downloaded
RUN ./gradlew build -x check -x detekt --parallel --continue > /dev/null 2>&1 || true

COPY tibiakt-core/ /app/tibiakt-core
COPY tibiakt-client/ /app/tibiakt-client
COPY tibiakt-server/ /app/tibiakt-server
RUN ./gradlew build -x test -x detekt tibiakt-server:shadowJar --parallel

FROM eclipse-temurin:19-jre-alpine
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

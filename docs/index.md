# TibiaKt

![GitHub Release](https://img.shields.io/github/v/release/Galarzaa90/TibiaKt)![GitHub Release Date](https://img.shields.io/github/release-date/Galarzaa90/TibiaKt)  
[![Maven Central](https://img.shields.io/maven-central/v/com.galarzaa/tibiakt-core)](https://central.sonatype.com/search?q=tibiakt)  
[![Docker Image Version](https://img.shields.io/docker/v/galarzaa90/tibiakt?sort=semver&logo=docker&logoColor=white)
![Docker Pulls](https://img.shields.io/docker/pulls/galarzaa90/tibiakt)
![Docker Image Size](https://img.shields.io/docker/image-size/galarzaa90/tibiakt?sort=semver)
](https://hub.docker.com/r/galarzaa90/tibiakt)

Kotlin library to fetch and parse Tibia.com pages.

## Features

- Support for most of Tibia.com pages, with complete data for characters, highscores, guilds, etc.
- Utility methods to build URLs, handle server save times, etctera.
- Complete data models for characters, guilds, etc.
- Easily extendable, use any HTTP client of your choice or use the included one.


## Usage example
Getting a character's data can be as simple as:
```kotlin
val client = TibiaKtClient()
val character: TibiaResponse<Character> = client.fetchCharacter("Galarzaa Fidera")
```

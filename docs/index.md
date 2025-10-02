# TibiaKt

Kotlin library to fetch and parse Tibia.com pages into data objects to use in your projects.

## Installation

The latest version available on maven central is:  
[![Maven Central](https://img.shields.io/maven-central/v/com.galarzaa/tibiakt-core)](https://central.sonatype.com/search?q=tibiakt)

The latest snapshot available is:

![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fcentral.sonatype.com%2Frepository%2Fmaven-snapshots%2Fcom%2Fgalarzaa%2Ftibiakt-core%2Fmaven-metadata.xml)

```kotlin
repositories {
    mavenCentral()
    // Only if you want to use snapshots
    maven {
        name = "Maven Central Snapshots"
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
    }
}

dependencies {
    implementation("com.galarzaa:tibiakt-core:{version}")
    // OR
    implementation("com.galarzaa:tibiakt-client:{version}")
}
```

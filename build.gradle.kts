val ktorVersion: String by project

plugins {
    kotlin("jvm") version "1.5.30"
    kotlin("plugin.serialization") version "1.5.30"
    id("org.jetbrains.dokka") version "1.5.0"
    `maven-publish`
}

group = "com.galarzaa"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
}


subprojects {
    apply(plugin = "maven-publish")
    configure<PublishingExtension> {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/Galarzaa90/TibiaKt")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}
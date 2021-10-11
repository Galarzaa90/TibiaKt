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
    afterEvaluate {
        publishing {
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
            publications {
                register<MavenPublication>("jar") {
                    from(components["java"])
                    version = "1.0-SNAPSHOT"
                    pom {
                        name.set("TibiaKt")
                        description.set("Tibia.com parser and client.")
                        scm {
                            connection.set("scm:git:git://github.com/Galarzaa90/TibiaKt.git")
                            developerConnection.set("scm:git:git://github.com/Galarzaa90/TibiaKt.git")
                            url.set("https://github.com/Galarzaa90/TibiaKt")
                        }
                        developers {
                            developer {
                                id.set("Galarzaa")
                                name.set("Allan Galarza")
                                url.set("https://galarzaa.com/")
                            }
                        }

                    }
                }
            }
        }
    }
}
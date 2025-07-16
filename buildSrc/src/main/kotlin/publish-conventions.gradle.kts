import org.jreleaser.model.Active

plugins {
    `maven-publish`
    id("org.jreleaser")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = Library.group
            version = Library.version
            description = Library.description

            pom {
                name = "TibiaKt"
                description = Library.description
                url = "https://github.com/Galarzaa90/TibiaKt"

                developers {
                    developer {
                        id = "Galarzaa"
                        name = "Allan Galarza"
                        url = "https://galarzaa.com/"
                    }
                }

                scm {
                    url = "https://github.com/Galarzaa90/TibiaKt"
                    connection = "scm:git:git://github.com/Galarzaa90/TibiaKt.git"
                    developerConnection =
                        "scm:git:ssh://git@github.com/Galarzaa90/TibiaKt.git"
                }
            }
        }
    }

    repositories {
        // Local repository
        maven {
            url = uri(layout.buildDirectory.dir("staging-deploy"))
        }
    }
}


jreleaser {
    gitRootSearch = true
    signing {
        active = Active.ALWAYS
        armored = true
    }
    val stagingDir = layout.buildDirectory.dir("staging-deploy")
    deploy {
        maven {
            mavenCentral {
                create("release") {
                    active = Active.ALWAYS
                    applyMavenCentralRules = true
                    snapshotSupported = true
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository(stagingDir.get().asFile.absolutePath)
                    retryDelay = 60
                    maxRetries = 120
                }
            }
        }
    }
}

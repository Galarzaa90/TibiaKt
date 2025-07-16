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

                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                        distribution.set("repo")
                    }
                }

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
    signing {
        active = Active.ALWAYS
        armored = true
    }
    val stagingDir = layout.buildDirectory.dir("staging-deploy")
    deploy {
        maven {
            mavenCentral {
                create("release") {
                    active = Active.RELEASE
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository(stagingDir.get().asFile.absolutePath)
                }
            }
            nexus2 {
                create("snapshot-deploy") {
                    active = Active.SNAPSHOT
                    snapshotUrl = "https://central.sonatype.com/repository/maven-snapshots/"
                    applyMavenCentralRules = true
                    snapshotSupported = true
                    closeRepository = true
                    releaseRepository = true
                    stagingRepository(stagingDir.get().asFile.absolutePath)
                }
            }
        }
    }
}

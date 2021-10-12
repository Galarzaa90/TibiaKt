apply(plugin = "java")
apply(plugin = "java-library")
apply(plugin = "maven-publish")
apply(plugin = "signing")

val signingKey: String? by project
val signingPassword: String? by project
val sonatypeUsername: String by project
val sonatypePassword: String by project

configure<SigningExtension> {
    //useInMemoryPgpKeys(signingKey, signingPassword)
}

configure<JavaPluginExtension> {
    withJavadocJar()
    withSourcesJar()
}

afterEvaluate {
    configure<PublishingExtension> {
        repositories {
//            maven {
//                name = "GitHubPackages"
//                url = uri("https://maven.pkg.github.com/Galarzaa90/TibiaKt")
//                credentials {
//                    username = System.getenv("GITHUB_ACTOR")
//                    password = System.getenv("GITHUB_TOKEN")
//                }
//            }
//            maven {
//                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/releases/")
//                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
//                url = snapshotsRepoUrl
//                credentials {
//                    username = sonatypeUsername
//                    password = sonatypePassword
//                }
//            }
        }
        publications {
            register<MavenPublication>("mavenJar") {
                from(components["java"])
                version = "1.0-SNAPSHOT"
                groupId = "com.galarzaa"
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

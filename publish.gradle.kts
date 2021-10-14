apply(plugin = "java")
apply(plugin = "java-library")
apply(plugin = "maven-publish")
apply(plugin = "signing")

val signingKey: String? by project
val signingPassword: String? by project

configure<SigningExtension> {
    //useInMemoryPgpKeys(signingKey, signingPassword)
}

configure<JavaPluginExtension> {
    withJavadocJar()
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

afterEvaluate {
    configure<PublishingExtension> {
        publications {
            register<MavenPublication>("mavenJar") {
                from(components["java"])
                version = "1.0-SNAPSHOT"
                groupId = Library.group
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

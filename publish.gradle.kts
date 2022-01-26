apply(plugin = "java")
apply(plugin = "java-library")
apply(plugin = "maven-publish")
apply(plugin = "signing")

configure<JavaPluginExtension> {
    withJavadocJar()
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

fun Project.publishing(action: PublishingExtension.() -> Unit) = configure(action)
fun Project.signing(configure: SigningExtension.() -> Unit): Unit = configure(configure)
val publications: PublicationContainer = (extensions.getByName("publishing") as PublishingExtension).publications

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("mavenJar") {
                from(components["java"])
                version = Library.version
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

signing {
    val signingId: String? = System.getenv("GPG_ID")
    val signingKey: String? = System.getenv("GPG_KEY")
    val signingPassword = System.getenv("GPG_PASS")
    if (signingKey != null && !Library.isSnapshot) {
        useInMemoryPgpKeys(
            signingId,
            signingKey,
            signingPassword,
        )
    }
    sign(publications)
}


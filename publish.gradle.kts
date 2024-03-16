/*
 * Copyright © 2024 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
            register<MavenPublication>("release") {
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
        repositories {
            maven {
                url = if (Library.isRelease) uri(Repo.releasesUrl) else uri(Repo.snapshotsUrl)
                credentials {
                    username = System.getenv("SONATYPE_USER") ?: ""
                    password = System.getenv("SONATYPE_PASSWORD") ?: ""
                }
            }
        }
    }
}

signing {
    val signingId: String? = System.getenv("GPG_ID")
    val signingKey: String? = System.getenv("GPG_KEY")
    val signingPassword = System.getenv("GPG_PASS")
    if (signingKey != null && !Library.isRelease) {
        useInMemoryPgpKeys(
            signingId,
            signingKey,
            signingPassword,
        )
        sign(publications)
    }
}

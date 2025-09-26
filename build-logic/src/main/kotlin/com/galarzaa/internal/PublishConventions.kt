/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.internal


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.jreleaser.gradle.plugin.JReleaserExtension
import org.jreleaser.model.Active


class PublishPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        // Apply required plugins
        pluginManager.apply("maven-publish")
        pluginManager.apply("org.jreleaser")

        val rootStaging = rootProject.layout.buildDirectory.dir("staging-deploy")

        // Publishing configuration
        extensions.configure<PublishingExtension> {
            publications {
                // Create a standard Java publication
                create<MavenPublication>("maven") {
                    // Expect Java component present (library/app with Java/Kotlin)
                    from(components.getByName("java"))

                    group = rootProject.group
                    version = rootProject.version as String
                    description = rootProject.description

                    pom {
                        name.set("TibiaKt")
                        description.set(project.description ?: "TibiaKt module")
                        url.set("https://github.com/Galarzaa90/TibiaKt")

                        licenses {
                            license {
                                name.set("Apache License 2.0")
                                url.set("https://www.apache.org/licenses/LICENSE-2.0")
                                distribution.set("repo")
                            }
                        }
                        developers {
                            developer {
                                id.set("Galarzaa")
                                name.set("Allan Galarza")
                                url.set("https://galarzaa.com/")
                            }
                        }
                        scm {
                            url.set("https://github.com/Galarzaa90/TibiaKt")
                            connection.set("scm:git:git://github.com/Galarzaa90/TibiaKt.git")
                            developerConnection.set("scm:git:ssh://git@github.com/Galarzaa90/TibiaKt.git")
                        }
                    }
                }
            }

            repositories {
                maven {
                    url = uri(rootStaging)
                }
            }
        }

        // JReleaser configuration
        extensions.configure<JReleaserExtension> {
            signing {
                active.set(Active.ALWAYS)
                armored.set(true)
            }

            deploy {
                maven {
                    mavenCentral {
                        create("release") {
                            active.set(Active.ALWAYS)
                            url.set("https://central.sonatype.com/api/v1/publisher")
                            stagingRepository(rootStaging.get().asFile.absolutePath)
                        }
                    }
                    nexus2 {
                        create("snapshot-deploy") {
                            active.set(Active.SNAPSHOT)
                            snapshotUrl.set("https://central.sonatype.com/repository/maven-snapshots/")
                            applyMavenCentralRules.set(true)
                            snapshotSupported.set(true)
                            closeRepository.set(true)
                            releaseRepository.set(true)
                            stagingRepository(rootStaging.get().asFile.absolutePath)
                        }
                    }
                }
            }
        }
    }
}

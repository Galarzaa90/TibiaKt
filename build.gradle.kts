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
plugins {
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.kover)
    alias(libs.plugins.git.version)
}


fun normalizedTag(): String = details.lastTag.removePrefix("v")

val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
val details = versionDetails()
version = if (details.commitDistance == 0 && details.isCleanTag) {
    // Exactly on a tag and the tree is clean
    normalizedTag()                      // e.g. 0.12.1
} else {
    // Anything else: ahead of tag, on a branch, or dirty working tree
    "${normalizedTag()}-SNAPSHOT"        // e.g. 0.12.1-SNAPSHOT
}
group = "com.galarzaa"

tasks.register("ciPrintVersion") {
    doLast {
        println("VERSION=${project.version}")
        println("LAST_TAG=${details.lastTag}")
        println("COMMIT_DISTANCE=${details.commitDistance}")
        println("BRANCH=${details.branchName}")
        println("CLEAN_TAG=${details.isCleanTag}")
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "Galarzaa90_TibiaKt")
        property("sonar.organization", "galarzaa90")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/kover/report.xml")
    }
}

fun String.isStable(): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    return stableKeyword || regex.matches(this)
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        // If the current version is stable, reject all non-stable candidates
        currentVersion.isStable() && !candidate.version.isStable()
    }
    gradleReleaseChannel = "current"
    outputFormatter = "html"
}

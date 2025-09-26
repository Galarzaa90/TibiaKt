import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure

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
    alias(libs.plugins.git.version) apply false
}


fun Provider<String>.orNullTrimmed() = orNull?.trim()?.takeUnless { it.isEmpty() }

val suppliedVersion = sequenceOf(
    providers.gradleProperty("VERSION"),
    providers.gradleProperty("version"),
    providers.environmentVariable("VERSION")
).mapNotNull { it.orNullTrimmed() }
 .firstOrNull()

val needGitVersion = (suppliedVersion == null) && file(".git").isDirectory
if (needGitVersion) apply(plugin = libs.plugins.git.version.get().pluginId)

fun normalizedTag(d: VersionDetails) = d.lastTag.removePrefix("v")

fun gitDetailsOrNull(): VersionDetails? =
    runCatching {
        @Suppress("UNCHECKED_CAST")
        (extensions.extraProperties["versionDetails"] as? Closure<VersionDetails>)?.call()
    }.getOrNull()

val computedVersion: String = when {
    suppliedVersion != null -> suppliedVersion
    needGitVersion -> {
        val versionDetails: Closure<VersionDetails> by extra
        val details = versionDetails()
        if (details.commitDistance == 0 && details.isCleanTag) {
            normalizedTag(details)
        } else {
            "${normalizedTag(details)}-SNAPSHOT"
        }
    }
    else -> "0.0.0-SNAPSHOT"
}

group = "com.galarzaa"
version = computedVersion


tasks.register("ciPrintVersion") {
    // Don’t resolve Git lazily at execution time if it’s not there.
    val d = gitDetailsOrNull()
    doLast {
        println("VERSION=$version")
        println("LAST_TAG=${d?.lastTag ?: "N/A"}")
        println("COMMIT_DISTANCE=${d?.commitDistance ?: "N/A"}")
        println("BRANCH=${d?.branchName ?: "N/A"}")
        println("CLEAN_TAG=${d?.isCleanTag ?: "N/A"}")
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

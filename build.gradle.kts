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


val sources = listOf(
    "gradleProp:VERSION" to providers.gradleProperty("VERSION"),
    "gradleProp:version" to providers.gradleProperty("version"),
    "env:VERSION" to providers.environmentVariable("VERSION")
)

val supplied = sources.asSequence()
    .mapNotNull { (label, p) -> p.orNull?.trim()?.takeIf { it.isNotEmpty() }?.let { label to it } }
    .firstOrNull()

val suppliedVersion = supplied?.second

if (supplied != null) {
    logger.lifecycle("version-supplied source={} value={}", supplied.first, supplied.second)
}

val needGitVersion = (suppliedVersion == null) && file(".git").isDirectory
if (needGitVersion) apply(plugin = libs.plugins.git.version.get().pluginId)

fun normalizedTag(d: VersionDetails) = d.lastTag.removePrefix("v")

fun gitDetailsOrNull(): VersionDetails? =
    runCatching {
        @Suppress("UNCHECKED_CAST")
        (extensions.extraProperties["versionDetails"] as? Closure<VersionDetails>)?.call()
    }.getOrNull()

var versionSource = "fallback"

val computedVersion: String = when {
    suppliedVersion != null -> {
        versionSource = "supplied"
        suppliedVersion
    }
    needGitVersion -> {
        val versionDetails: Closure<VersionDetails> by extra
        val details = versionDetails()
        versionSource = "git(tag=${details.lastTag}, distance=${details.commitDistance}, clean=${details.isCleanTag})"
        if (details.commitDistance == 0 && details.isCleanTag) {
            normalizedTag(details)
        } else {
            "${normalizedTag(details)}-SNAPSHOT"
        }
    }
    else -> "0.0.0-SNAPSHOT"
}

logger.lifecycle("version-computed source={} value={}", versionSource, computedVersion)

group = "com.galarzaa"
version = computedVersion
description =  "Tibia.com parser and client."

allprojects {
    group = rootProject.group
    version = rootProject.version
    description = rootProject.description
}

sonarqube {
    properties {
        property("sonar.projectKey", "Galarzaa90_TibiaKt")
        property("sonar.organization", "galarzaa90")
        property("sonar.projectVersion", rootProject.version.toString())
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

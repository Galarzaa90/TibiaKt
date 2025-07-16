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

object Library {
    const val group = "com.galarzaa"
    val version: String
        get() {
        val envVersion = System.getenv("VERSION")
        if (!envVersion.isNullOrBlank()) return envVersion.removePrefix("v")

        val tag = System.getenv("GITHUB_TAG_NAME")
        val branch = System.getenv("GITHUB_BRANCH_NAME")
        return when {
            !tag.isNullOrBlank() -> tag.removePrefix("v")
            !branch.isNullOrBlank() && branch.startsWith("refs/heads/") ->
                branch.removePrefix("refs/heads/").replace("/", "-") + "-SNAPSHOT"
            else -> "undefined"
        }
    }
    val isSnapshot: Boolean get() = version.endsWith("-SNAPSHOT")
    val isRelease: Boolean get() = !isSnapshot && !isUndefined
    val isUndefined get() = version == "undefined"

    const val description = "Tibia.com parser and client."
}

object Repo {
    const val releasesUrl = "https://s01.oss.sonatype.org/content/repositories/releases/"
    const val snapshotsUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
}

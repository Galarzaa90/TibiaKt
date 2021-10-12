object Library {
    const val group = "com.galarzaa"
    val version: String
        get() {
            val tag = System.getenv("GITHUB_TAG_NAME")
            val branch = System.getenv("GITHUB_BRANCH_NAME")
            return when {
                !tag.isNullOrBlank() -> tag
                !branch.isNullOrBlank() && branch.startsWith("refs/heads/") ->
                    branch.substringAfter("refs/heads/").replace("/", "-") + "-SNAPSHOT"
                else -> "undefined"
            }
        }
    val isSnapshot: Boolean get() = version.endsWith("-SNAPSHOT")
    val isRelease: Boolean get() = !isSnapshot && !isUndefined
    val isUndefined get() = version == "undefined"
}

object Repo {
    const val releasesUrl = "https://s01.oss.sonatype.org/content/repositories/releases/"
    const val snapshotsUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
}
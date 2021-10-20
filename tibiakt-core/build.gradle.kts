val kotlinVersion: String by project
val ktorVersion: String by project
val kotestVersion: String by project
val jsoupVersion: String by project

apply("../publish.gradle.kts")
apply(plugin = "kover")

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jsoup:jsoup:$jsoupVersion")

    implementation("io.ktor:ktor-serialization:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:1.2.6")

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.30")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
}

tasks.test {
    extensions.configure(kotlinx.kover.api.KoverTaskExtension::class) {
        generateXml = true
        generateHtml = true
        coverageEngine = kotlinx.kover.api.CoverageEngine.INTELLIJ
    }
}
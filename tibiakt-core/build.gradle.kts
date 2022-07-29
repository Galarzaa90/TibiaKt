import java.net.URL
val kotlinVersion: String by project
val ktorVersion: String by project
val kotestVersion: String by project
val jsoupVersion: String by project
val kotlinLoggingVersion: String by project
val logbackVersion: String by project
val kotlinxDatetime: String by project

apply("../publish.gradle.kts")

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

    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    api("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDatetime")

    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
}


tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        configureEach {
            includes.from("Module.md")
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("https://github.com/Galarzaa90/TibiaKt/blob/main/tibiakt-core/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
            externalDocumentationLink {
                url.set(URL("https://api.ktor.io/"))
            }
        }
    }
}
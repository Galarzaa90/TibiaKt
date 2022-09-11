import java.net.URL
val ktorVersion: String by project
val kotestVersion: String by project
val kotlinLoggingVersion: String by project

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
    api(project(":tibiakt-core"))
    implementation("io.ktor:ktor-client-encoding:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-encoding:$ktorVersion")

    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("io.ktor:ktor-client-mock:${ktorVersion}")
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        configureEach {
            includes.from("Module.md")
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("https://github.com/Galarzaa90/TibiaKt/blob/main/tibiakt-client/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
            externalDocumentationLink {
                url.set(URL("https://api.ktor.io/"))
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

kotlin {
    explicitApi()
}

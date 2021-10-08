plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.30" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.30" apply false
    id("org.jetbrains.dokka") version "1.5.0" apply true
}

group = "com.galarzaa"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}


tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("dokkaCustomMultiModuleOutput"))
}
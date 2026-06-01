plugins {
    alias(libs.plugins.kotlin.jvm)
}

apply(from = rootProject.file("gradle/publishing.gradle.kts"))

kotlin {
    explicitApi()
    jvmToolchain(17)
}

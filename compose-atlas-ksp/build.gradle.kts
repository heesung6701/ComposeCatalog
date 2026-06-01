plugins {
    alias(libs.plugins.kotlin.jvm)
}

apply(from = rootProject.file("gradle/publishing.gradle.kts"))

kotlin {
    explicitApi()
    jvmToolchain(17)
}

dependencies {
    implementation(project(":compose-atlas-annotation"))
    implementation(libs.ksp.api)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
    testImplementation(libs.junit)
}

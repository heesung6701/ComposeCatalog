import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

plugins {
    alias(libs.plugins.kotlin.jvm)
}

apply(from = rootProject.file("gradle/publishing.gradle.kts"))

kotlin {
    explicitApi()
    jvmToolchain(17)
}

java {
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    implementation(project(":compose-atlas-annotation"))
    implementation(libs.ksp.api)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
    testImplementation(libs.junit)
}

extensions.configure<PublishingExtension>("publishing") {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "compose-atlas-ksp"
        }
    }
}

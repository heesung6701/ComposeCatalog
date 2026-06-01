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

extensions.configure<PublishingExtension>("publishing") {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "compose-atlas-annotation"
        }
    }
}

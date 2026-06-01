import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

plugins {
    `java-platform`
}

apply(from = rootProject.file("gradle/publishing.gradle.kts"))

javaPlatform {
    allowDependencies()
}

dependencies {
    constraints {
        api(project(":compose-atlas-annotation"))
        api(project(":compose-atlas-runtime"))
        api(project(":compose-atlas-navigation"))
        api(project(":compose-atlas-ksp"))
    }
}

extensions.configure<PublishingExtension>("publishing") {
    publications {
        create<MavenPublication>("maven") {
            from(components["javaPlatform"])
            artifactId = "compose-atlas-bom"
        }
    }
}

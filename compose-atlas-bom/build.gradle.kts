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

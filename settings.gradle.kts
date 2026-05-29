pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ComposeCatalog"

include(
    ":compose-atlas-annotation",
    ":compose-atlas-runtime",
    ":compose-atlas-navigation",
    ":compose-atlas-ksp",
    ":sample-app",
)

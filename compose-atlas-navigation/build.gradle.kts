plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

apply(from = rootProject.file("gradle/publishing.gradle.kts"))

android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    namespace = "io.github.composecatalog.atlas.navigation"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

}

kotlin {
    explicitApi()
}

dependencies {
    api(project(":compose-atlas-runtime"))
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    api(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
}

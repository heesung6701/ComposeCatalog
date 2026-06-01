import org.gradle.api.publish.PublishingExtension
import org.gradle.plugins.signing.SigningExtension

apply(plugin = "maven-publish")
apply(plugin = "signing")
apply(plugin = "com.vanniktech.maven.publish")

val mavenGroupId = providers.gradleProperty("GROUP").get()
val mavenVersionName = providers.gradleProperty("VERSION_NAME").get()

group = mavenGroupId
version = mavenVersionName

extensions.configure<PublishingExtension>("publishing") {
    repositories {
        maven {
            name = "localBuild"
            url = rootProject.layout.buildDirectory.dir("repo").get().asFile.toURI()
        }
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/heesung6701/ComposeCatalog")
            credentials {
                username = providers.gradleProperty("gpr.user")
                    .orElse(providers.environmentVariable("GITHUB_ACTOR"))
                    .orElse(providers.environmentVariable("GITHUB_USERNAME"))
                    .orNull
                password = providers.gradleProperty("gpr.key")
                    .orElse(providers.environmentVariable("GITHUB_TOKEN"))
                    .orElse(providers.environmentVariable("GITHUB_PACKAGE_TOKEN"))
                    .orNull
            }
        }
    }
}

val mavenPublishing = extensions.getByName("mavenPublishing")
val publishToMavenCentralMethod = mavenPublishing.javaClass.methods
    .firstOrNull { method ->
        method.name == "publishToMavenCentral" &&
            method.parameterCount == 1 &&
            method.parameterTypes[0] == Boolean::class.javaPrimitiveType
    }
    ?: mavenPublishing.javaClass.methods.first { method ->
        method.name == "publishToMavenCentral" && method.parameterCount == 0
    }
if (publishToMavenCentralMethod.parameterCount == 1) {
    publishToMavenCentralMethod.invoke(mavenPublishing, true)
} else {
    publishToMavenCentralMethod.invoke(mavenPublishing)
}

extensions.configure<SigningExtension>("signing") {
    val signingKey = providers.gradleProperty("signingInMemoryKey")
        .orElse(providers.environmentVariable("SIGNING_IN_MEMORY_KEY"))
        .orNull
        ?.takeIf { it.isNotBlank() }
    val signingPassword = providers.gradleProperty("signingInMemoryKeyPassword")
        .orElse(providers.environmentVariable("SIGNING_IN_MEMORY_KEY_PASSWORD"))
        .orNull
        ?.takeIf { it.isNotBlank() }

    isRequired = signingKey != null && !mavenVersionName.endsWith("SNAPSHOT")
    if (signingKey != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(extensions.getByType<PublishingExtension>().publications)
    }
}

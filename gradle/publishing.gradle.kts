import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.plugins.signing.SigningExtension

apply(plugin = "maven-publish")
apply(plugin = "signing")

val mavenGroupId = providers.gradleProperty("GROUP").get()
val mavenVersionName = providers.gradleProperty("VERSION_NAME").get()
val pomName = providers.gradleProperty("POM_NAME").get()
val pomDescription = providers.gradleProperty("POM_DESCRIPTION").get()
val pomUrl = providers.gradleProperty("POM_URL").get()
val pomLicenseName = providers.gradleProperty("POM_LICENSE_NAME").get()
val pomLicenseUrl = providers.gradleProperty("POM_LICENSE_URL").get()
val pomDeveloperId = providers.gradleProperty("POM_DEVELOPER_ID").get()
val pomDeveloperName = providers.gradleProperty("POM_DEVELOPER_NAME").get()
val pomScmUrl = providers.gradleProperty("POM_SCM_URL").get()
val pomScmConnection = providers.gradleProperty("POM_SCM_CONNECTION").get()
val pomScmDeveloperConnection = providers.gradleProperty("POM_SCM_DEVELOPER_CONNECTION").get()

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

    publications.withType<MavenPublication>().configureEach {
        pom {
            name.set("$pomName ${project.name}")
            description.set(pomDescription)
            url.set(pomUrl)
            licenses {
                license {
                    name.set(pomLicenseName)
                    url.set(pomLicenseUrl)
                }
            }
            developers {
                developer {
                    id.set(pomDeveloperId)
                    name.set(pomDeveloperName)
                }
            }
            scm {
                url.set(pomScmUrl)
                connection.set(pomScmConnection)
                developerConnection.set(pomScmDeveloperConnection)
            }
        }
    }
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

package io.github.composecatalog.atlas.navigation

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AtlasNavigation3MigrationTest {
    @Test
    fun navigationModuleUsesNavigation3InsteadOfNavigationCompose() {
        val projectDir = locateRepositoryRoot()
        val buildFile = projectDir.resolve("compose-atlas-navigation/build.gradle.kts").readText()
        val navHostSource = projectDir
            .resolve("compose-atlas-navigation/src/main/kotlin/io/github/composecatalog/atlas/navigation/AtlasNavHost.kt")
            .readText()

        assertTrue(
            "navigation module should depend on Navigation3 runtime",
            buildFile.contains("androidx.navigation3.navigation3.runtime"),
        )
        assertTrue(
            "navigation module should depend on Navigation3 UI",
            buildFile.contains("androidx.navigation3.navigation3.ui"),
        )
        assertFalse(
            "navigation module should no longer expose Navigation Compose",
            buildFile.contains("androidx.navigation.compose"),
        )
        assertTrue(
            "AtlasNavHost should render Navigation3 NavDisplay",
            navHostSource.contains("NavDisplay("),
        )
        assertFalse(
            "AtlasNavHost public API should not expose NavHostController after Navigation3 migration",
            navHostSource.contains("NavHostController"),
        )
    }

    private fun locateRepositoryRoot(): Path {
        var current = Path.of(System.getProperty("user.dir")).toAbsolutePath()
        while (current.parent != null) {
            if (current.resolve("settings.gradle.kts").exists() &&
                current.resolve("compose-atlas-navigation").exists()
            ) {
                return current
            }
            current = current.parent
        }
        throw IllegalStateException("Could not locate ComposeCatalog repository root")
    }
}

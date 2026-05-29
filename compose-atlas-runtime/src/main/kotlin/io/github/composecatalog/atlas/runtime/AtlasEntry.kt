package io.github.composecatalog.atlas.runtime

import androidx.compose.runtime.Composable

public data class AtlasEntry(
    val title: String,
    val group: String,
    val description: String,
    val route: String,
    val order: Int = 0,
    val deepLink: String = "atlas://$route",
    val content: @Composable () -> Unit,
)

public interface AtlasRegistry {
    public val entries: List<AtlasEntry>
}

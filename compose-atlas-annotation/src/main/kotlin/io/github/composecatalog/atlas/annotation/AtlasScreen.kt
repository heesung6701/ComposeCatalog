package io.github.composecatalog.atlas.annotation

/**
 * Marks a zero-argument @Composable function as a discoverable Compose Atlas screen.
 *
 * The KSP processor validates that annotated functions are composable and have no parameters,
 * then emits registry metadata and route bindings at compile time.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
public annotation class AtlasScreen(
    val title: String = "",
    val description: String = "",
    val route: String = "",
    val order: Int = 0,
)

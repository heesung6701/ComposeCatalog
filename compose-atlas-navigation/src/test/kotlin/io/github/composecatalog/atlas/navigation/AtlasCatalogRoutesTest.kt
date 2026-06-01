package io.github.composecatalog.atlas.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class AtlasCatalogRoutesTest {
    @Test
    fun groupRoutesPreserveMultiDepthPathAndEncodeSegments() {
        assertEquals(
            "__atlas_catalog_group__/Components/Input",
            atlasCatalogGroupRoute(listOf("Components", "Input")),
        )
        assertEquals(
            "__atlas_catalog_group__/Patterns/Date%20%26%20Time",
            atlasCatalogGroupRoute(listOf("Patterns", "Date & Time")),
        )
    }
}

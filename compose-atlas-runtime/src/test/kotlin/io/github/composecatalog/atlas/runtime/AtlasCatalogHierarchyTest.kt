package io.github.composecatalog.atlas.runtime

import org.junit.Assert.assertEquals
import org.junit.Test

class AtlasCatalogHierarchyTest {
    @Test
    fun buildsMultiDepthGroupsFromSlashSeparatedGroupNames() {
        val entries = listOf(
            entry(title = "Button", group = "Components/Input"),
            entry(title = "Text", group = "Components/Display"),
            entry(title = "Catalog List", group = "Patterns / Collections"),
            entry(title = "Ungrouped", group = ""),
        )

        val root = AtlasCatalogHierarchy.build(
            entries = entries,
            routeForGroup = { path -> "group/${path.joinToString("/")}" },
        )

        assertEquals(listOf("Components", "Patterns"), root.childGroups.map { it.title })
        assertEquals(listOf("Ungrouped"), root.entries.map { it.title })

        val components = root.childGroups.single { it.title == "Components" }
        assertEquals(listOf("Display", "Input"), components.childGroups.map { it.title })
        assertEquals("group/Components", components.route)

        val input = components.childGroups.single { it.title == "Input" }
        assertEquals(listOf("Button"), input.entries.map { it.title })
        assertEquals("group/Components/Input", input.route)

        val patterns = root.childGroups.single { it.title == "Patterns" }
        val collections = patterns.childGroups.single { it.title == "Collections" }
        assertEquals(listOf("Catalog List"), collections.entries.map { it.title })
    }

    private fun entry(title: String, group: String): AtlasEntry = AtlasEntry(
        title = title,
        group = group,
        description = "",
        route = title.lowercase().replace(" ", "-"),
        content = {},
    )
}

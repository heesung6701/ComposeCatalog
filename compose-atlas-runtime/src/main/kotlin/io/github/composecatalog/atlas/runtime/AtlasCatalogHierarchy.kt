package io.github.composecatalog.atlas.runtime

public data class AtlasCatalogGroup(
    val title: String,
    val path: List<String>,
    val route: String,
    val childGroups: List<AtlasCatalogGroup>,
    val entries: List<AtlasEntry>,
)

public object AtlasCatalogHierarchy {
    public fun build(
        entries: List<AtlasEntry>,
        routeForGroup: (List<String>) -> String,
    ): AtlasCatalogGroup {
        val normalizedEntries = entries.sortedWith(
            compareBy<AtlasEntry> { it.order }.thenBy { it.title }.thenBy { it.route },
        )

        fun buildGroup(title: String, path: List<String>, groupEntries: List<AtlasEntry>): AtlasCatalogGroup {
            val directEntries = groupEntries.filter { it.groupPath().size == path.size }
            val childTitles = groupEntries
                .mapNotNull { it.groupPath().getOrNull(path.size) }
                .distinct()
                .sorted()
            val childGroups = childTitles.map { childTitle ->
                val childPath = path + childTitle
                buildGroup(
                    title = childTitle,
                    path = childPath,
                    groupEntries = groupEntries.filter { it.groupPath().startsWith(childPath) },
                )
            }
            return AtlasCatalogGroup(
                title = title,
                path = path,
                route = if (path.isEmpty()) "" else routeForGroup(path),
                childGroups = childGroups,
                entries = directEntries,
            )
        }

        return buildGroup(
            title = "Compose Atlas",
            path = emptyList(),
            groupEntries = normalizedEntries,
        )
    }
}

public fun AtlasEntry.groupPath(): List<String> = group
    .split('/')
    .map { it.trim() }
    .filter { it.isNotEmpty() }

private fun List<String>.startsWith(prefix: List<String>): Boolean =
    size >= prefix.size && subList(0, prefix.size) == prefix

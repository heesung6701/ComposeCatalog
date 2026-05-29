package io.github.composecatalog.atlas.runtime

public object AtlasSearchEngine {
    public fun filter(entries: List<AtlasEntry>, query: String): List<AtlasEntry> {
        val normalized = query.trim().lowercase()
        if (normalized.isEmpty()) return entries
        return entries.filter { entry ->
            entry.title.contains(normalized, ignoreCase = true) ||
                entry.group.contains(normalized, ignoreCase = true) ||
                entry.description.contains(normalized, ignoreCase = true)
        }
    }
}

package io.github.composecatalog.atlas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import io.github.composecatalog.atlas.runtime.AtlasCatalogGroup
import io.github.composecatalog.atlas.runtime.AtlasCatalogHierarchy
import io.github.composecatalog.atlas.runtime.AtlasCatalogScreen
import io.github.composecatalog.atlas.runtime.AtlasEntry
import io.github.composecatalog.atlas.runtime.AtlasRegistry
import io.github.composecatalog.atlas.runtime.allEntries

public const val AtlasCatalogRoute: String = "__atlas_catalog__"
public const val AtlasCatalogGroupRoutePrefix: String = "__atlas_catalog_group__"

@Composable
public fun rememberAtlasBackStack(
    startDestination: String = AtlasCatalogRoute,
): MutableList<String> = remember(startDestination) { mutableStateListOf(startDestination) }

@Composable
public fun AtlasNavHost(
    registry: AtlasRegistry,
    startDestination: String = AtlasCatalogRoute,
    backStack: MutableList<String> = rememberAtlasBackStack(startDestination),
) {
    AtlasNavHost(
        entries = registry.entries,
        startDestination = startDestination,
        backStack = backStack.ensureStartDestination(startDestination),
    )
}

@Composable
public fun AtlasNavHost(
    entries: List<AtlasEntry>,
    startDestination: String = AtlasCatalogRoute,
    backStack: MutableList<String> = rememberAtlasBackStack(startDestination),
) {
    val hierarchy = remember(entries) {
        AtlasCatalogHierarchy.build(entries = entries, routeForGroup = ::atlasCatalogGroupRoute)
    }
    val groupByRoute = remember(hierarchy) {
        hierarchy.childGroups.flattenGroups().associateBy { it.route }
    }
    val entryByRoute = remember(entries) { entries.associateBy { it.route } }
    val atlasBackStack = backStack.ensureStartDestination(startDestination)

    NavDisplay(
        backStack = atlasBackStack,
        entryProvider = { route ->
            NavEntry(route) { currentRoute ->
                when (currentRoute) {
                    AtlasCatalogRoute -> {
                        AtlasCatalogScreen(
                            title = hierarchy.title,
                            groups = hierarchy.childGroups,
                            entries = hierarchy.entries,
                            searchEntries = entries,
                            onGroupSelected = { atlasBackStack.navigateTo(it.route) },
                            onEntrySelected = { atlasBackStack.navigateTo(it.route) },
                        )
                    }
                    in groupByRoute -> {
                        val group = groupByRoute.getValue(currentRoute)
                        AtlasCatalogScreen(
                            title = group.path.joinToString(" / "),
                            groups = group.childGroups,
                            entries = group.entries,
                            searchEntries = group.allEntries(),
                            onGroupSelected = { atlasBackStack.navigateTo(it.route) },
                            onEntrySelected = { atlasBackStack.navigateTo(it.route) },
                        )
                    }
                    in entryByRoute -> {
                        entryByRoute.getValue(currentRoute).content()
                    }
                    else -> {
                        throw IllegalStateException("Unknown Compose Atlas route: $currentRoute")
                    }
                }
            }
        },
    )
}

public fun atlasCatalogGroupRoute(path: List<String>): String =
    buildString {
        append(AtlasCatalogGroupRoutePrefix)
        path.forEach { segment ->
            append('/')
            append(segment.encodeRouteSegment())
        }
    }

private fun MutableList<String>.ensureStartDestination(startDestination: String): MutableList<String> {
    if (isEmpty()) {
        add(startDestination)
    }
    return this
}

private fun MutableList<String>.navigateTo(route: String) {
    add(route)
}

private fun List<AtlasCatalogGroup>.flattenGroups(): List<AtlasCatalogGroup> =
    flatMap { group -> listOf(group) + group.childGroups.flattenGroups() }

private fun String.encodeRouteSegment(): String =
    URLEncoder.encode(this, StandardCharsets.UTF_8.toString()).replace("+", "%20")

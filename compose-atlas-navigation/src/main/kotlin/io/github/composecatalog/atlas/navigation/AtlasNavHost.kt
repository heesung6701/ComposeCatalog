package io.github.composecatalog.atlas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
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
public fun AtlasNavHost(
    registry: AtlasRegistry,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AtlasCatalogRoute,
) {
    AtlasNavHost(
        entries = registry.entries,
        navController = navController,
        startDestination = startDestination,
    )
}

@Composable
public fun AtlasNavHost(
    entries: List<AtlasEntry>,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AtlasCatalogRoute,
) {
    val hierarchy = remember(entries) {
        AtlasCatalogHierarchy.build(entries = entries, routeForGroup = ::atlasCatalogGroupRoute)
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(AtlasCatalogRoute) {
            AtlasCatalogScreen(
                title = hierarchy.title,
                groups = hierarchy.childGroups,
                entries = hierarchy.entries,
                searchEntries = entries,
                onGroupSelected = { navController.navigate(it.route) },
                onEntrySelected = { navController.navigate(it.route) },
            )
        }
        hierarchy.childGroups.flattenGroups().forEach { group ->
            composable(group.route) {
                AtlasCatalogScreen(
                    title = group.path.joinToString(" / "),
                    groups = group.childGroups,
                    entries = group.entries,
                    searchEntries = group.allEntries(),
                    onGroupSelected = { navController.navigate(it.route) },
                    onEntrySelected = { navController.navigate(it.route) },
                )
            }
        }
        entries.forEach { entry ->
            composable(
                route = entry.route,
                deepLinks = listOf(navDeepLink { uriPattern = entry.deepLink }),
            ) {
                entry.content()
            }
        }
    }
}

public fun atlasCatalogGroupRoute(path: List<String>): String =
    buildString {
        append(AtlasCatalogGroupRoutePrefix)
        path.forEach { segment ->
            append('/')
            append(segment.encodeRouteSegment())
        }
    }

private fun List<AtlasCatalogGroup>.flattenGroups(): List<AtlasCatalogGroup> =
    flatMap { group -> listOf(group) + group.childGroups.flattenGroups() }

private fun String.encodeRouteSegment(): String =
    URLEncoder.encode(this, StandardCharsets.UTF_8.toString()).replace("+", "%20")

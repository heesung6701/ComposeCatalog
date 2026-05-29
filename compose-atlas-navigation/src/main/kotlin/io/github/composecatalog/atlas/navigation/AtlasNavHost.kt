package io.github.composecatalog.atlas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import io.github.composecatalog.atlas.runtime.AtlasCatalogScreen
import io.github.composecatalog.atlas.runtime.AtlasEntry
import io.github.composecatalog.atlas.runtime.AtlasRegistry

public const val AtlasCatalogRoute: String = "__atlas_catalog__"

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
    NavHost(navController = navController, startDestination = startDestination) {
        composable(AtlasCatalogRoute) {
            AtlasCatalogScreen(entries = entries, onEntrySelected = { navController.navigate(it.route) })
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

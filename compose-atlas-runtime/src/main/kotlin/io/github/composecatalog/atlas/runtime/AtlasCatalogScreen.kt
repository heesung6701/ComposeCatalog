package io.github.composecatalog.atlas.runtime

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
public fun AtlasCatalogScreen(
    entries: List<AtlasEntry>,
    modifier: Modifier = Modifier,
    onEntrySelected: (AtlasEntry) -> Unit = {},
) {
    var query by remember { mutableStateOf("") }
    val visibleEntries = remember(entries, query) {
        AtlasSearchEngine.filter(entries, query)
            .sortedWith(compareBy<AtlasEntry> { it.group }.thenBy { it.order }.thenBy { it.title })
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Compose Atlas", style = MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search") },
                modifier = Modifier.padding(top = 12.dp),
                singleLine = true,
            )
            LazyColumn(modifier = Modifier.padding(top = 12.dp)) {
                items(visibleEntries, key = { it.route }) { entry ->
                    Column(
                        modifier = Modifier
                            .clickable { onEntrySelected(entry) }
                            .padding(vertical = 8.dp),
                    ) {
                        Text(entry.title, style = MaterialTheme.typography.titleMedium)
                        if (entry.group.isNotBlank()) {
                            Text(entry.group, style = MaterialTheme.typography.labelMedium)
                        }
                        if (entry.description.isNotBlank()) {
                            Text(entry.description, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

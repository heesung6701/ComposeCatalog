package io.github.composecatalog.demo.patterns.collections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.composecatalog.atlas.annotation.AtlasScreen
import io.github.composecatalog.demo.components.ColumnWithPadding
import io.github.composecatalog.demo.components.DemoScaffold

@AtlasScreen(
    title = "Catalog List",
    description = "A screen that represents a component list inside a design-system catalog.",
    order = 40,
)
@Composable
fun CatalogListDemo() {
    val sections = listOf(
        "Actions" to "Buttons, icon buttons, and click targets",
        "Forms" to "Text fields, switches, checkboxes, and validation states",
        "Surfaces" to "Cards, sheets, dialogs, and containers",
        "Feedback" to "Snackbars, progress, and empty states",
    )

    DemoScaffold(
        title = "Catalog List",
        description = "Pretend this is a category page in a Compose Catalog style demo app.",
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            sections.forEach { (title, description) ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    ColumnWithPadding {
                        Text(title, style = MaterialTheme.typography.titleMedium)
                        Text(description, style = MaterialTheme.typography.bodyMedium)
                        AssistChip(onClick = {}, label = { Text("Open $title") })
                    }
                }
            }
        }
    }
}

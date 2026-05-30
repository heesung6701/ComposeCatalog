package io.github.composecatalog.demo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.composecatalog.atlas.annotation.AtlasScreen

@AtlasScreen(
    title = "Cards",
    group = "Components / Surfaces",
    description = "Surface and card samples that mimic a catalog detail page.",
    order = 30,
)
@Composable
fun CardsDemo() {
    DemoScaffold(
        title = "Cards",
        description = "Compare base cards with elevated cards as reusable component examples.",
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(DemoSpacing)) {
            Card(modifier = Modifier.fillMaxWidth()) {
                ColumnWithPadding {
                    Text("Default card", style = MaterialTheme.typography.titleMedium)
                    Text("Use for grouped content with the default Material 3 surface treatment.")
                }
            }
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                ColumnWithPadding {
                    Text("Elevated card", style = MaterialTheme.typography.titleMedium)
                    Text("Useful when the catalog wants to emphasize a component preview.")
                }
            }
        }
    }
}

package io.github.composecatalog.sample.demos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.composecatalog.atlas.annotation.AtlasScreen

@AtlasScreen(
    title = "Text",
    group = "Components/Display",
    description = "Typography preview sample",
    order = 20,
)
@Composable
fun TextDemo() {
    Column(modifier = Modifier.padding(24.dp)) {
        Text("Headline", style = MaterialTheme.typography.headlineMedium)
        Text("Body text", style = MaterialTheme.typography.bodyLarge)
    }
}

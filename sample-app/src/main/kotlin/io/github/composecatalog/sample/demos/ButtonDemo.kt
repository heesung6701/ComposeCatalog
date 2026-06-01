package io.github.composecatalog.sample.demos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.composecatalog.atlas.annotation.AtlasScreen

@AtlasScreen(
    title = "Button",
    description = "Basic Material 3 button sample",
    order = 10,
)
@Composable
fun ButtonDemo() {
    Column(modifier = Modifier.padding(24.dp)) {
        Text("Button Demo")
        Button(onClick = {}) {
            Text("Click me")
        }
    }
}

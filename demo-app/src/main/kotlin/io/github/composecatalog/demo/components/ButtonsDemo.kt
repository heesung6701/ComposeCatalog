package io.github.composecatalog.demo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.composecatalog.atlas.annotation.AtlasScreen

@AtlasScreen(
    title = "Buttons",
    group = "Components / Actions",
    description = "Common Material 3 button variants shown as a component test screen.",
    order = 10,
)
@Composable
fun ButtonsDemo() {
    DemoScaffold(
        title = "Buttons",
        description = "Validate filled, elevated, tonal, and outlined actions in one place.",
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(onClick = {}) { Text("Primary") }
                ElevatedButton(onClick = {}) { Text("Elevated") }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                FilledTonalButton(onClick = {}) { Text("Tonal") }
                OutlinedButton(onClick = {}) { Text("Outlined") }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "These screens are registered with @AtlasScreen and surfaced by the generated registry.",
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

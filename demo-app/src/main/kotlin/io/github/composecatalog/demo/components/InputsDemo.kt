package io.github.composecatalog.demo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.composecatalog.atlas.annotation.AtlasScreen

@AtlasScreen(
    title = "Inputs",
    group = "Components / Forms",
    description = "Text field and switch examples for form-state smoke testing.",
    order = 20,
)
@Composable
fun InputsDemo() {
    var name by remember { mutableStateOf("Compose Catalog") }
    var enabled by remember { mutableStateOf(true) }

    DemoScaffold(
        title = "Inputs",
        description = "Exercise simple stateful input components inside a catalog entry.",
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(DemoSpacing)) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Component name") },
                supportingText = { Text("Current value: $name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
            LabeledRow(title = "Enabled", description = "Toggle local state") {
                Switch(checked = enabled, onCheckedChange = { enabled = it })
            }
        }
    }
}

package io.github.composecatalog.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import io.github.composecatalog.atlas.generated.GeneratedAtlasRegistry
import io.github.composecatalog.atlas.navigation.AtlasNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AtlasNavHost(registry = GeneratedAtlasRegistry)
            }
        }
    }
}

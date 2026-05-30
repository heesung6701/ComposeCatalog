package io.github.composecatalog.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import io.github.composecatalog.atlas.generated.GeneratedAtlasRegistry
import io.github.composecatalog.atlas.navigation.AtlasNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    AtlasNavHost(registry = GeneratedAtlasRegistry)
                }
            }
        }
    }
}

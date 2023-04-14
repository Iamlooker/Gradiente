package com.looker.gradiente.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.looker.gradiente.ui.home_page.HomePage
import com.looker.gradiente.ui.theme.GradienteTheme
import dagger.hilt.android.AndroidEntryPoint

// TODO: Check for current wallpaper
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gradiente {
                HomePage()
            }
        }
    }
}

@Composable
fun Gradiente(content: @Composable (PaddingValues) -> Unit) {
    GradienteTheme {
        Surface {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = content
            )
        }
    }
}
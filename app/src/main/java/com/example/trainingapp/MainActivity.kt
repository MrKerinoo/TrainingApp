package com.example.trainingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.trainingapp.ui.theme.TrainingAppTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {

            TrainingAppTheme(
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TrainingApp()
                }
            }

            updateLocale(1)
        }
    }

    private fun updateLocale(language: Int) {
        val locale = when (language) {
            1 -> Locale("en")
            2 -> Locale("sk")
            3 -> Locale("cz")
            else -> Locale("en")
        }
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
    }
}

package com.example.trainingapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.trainingapp.ui.AppViewModelProvider
import com.example.trainingapp.ui.profile.UserViewModel
import com.example.trainingapp.ui.theme.TrainingAppTheme
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this, AppViewModelProvider.Factory).get(UserViewModel::class.java)

        var darkTheme: Boolean
        lifecycleScope.launch {
            darkTheme = userViewModel.userUiState.userDetails.darkMode
        }

        setContent {
            // Collect state
            val darkModeState by userViewModel.darkMode.collectAsState(initial = false)
            val userUiState = userViewModel.userUiState
            val selectedLanguageState = userViewModel.userUiState.userDetails.lang

            Log.d("MainActivity", "MAIN   Dark Mode State: $darkModeState")
            Log.d("MainActivity", "MAIN   Selected Language: ${userUiState.userDetails.lang}")

            TrainingAppTheme(
                darkTheme = userUiState.userDetails.darkMode
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TrainingApp()
                }
            }

            updateLocale(selectedLanguageState)
        }
    }

    private fun updateLocale(language: String) {
        val locale = when (language) {
            "English" -> Locale("en")
            "Slovak" -> Locale("sk")
            "Czech" -> Locale("cz")
            else -> Locale("en")
        }
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}

package com.example.trainingapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.H
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trainingapp.ui.history.HistoryDestination
import com.example.trainingapp.ui.history.HistoryScreen
import com.example.trainingapp.ui.home.HomeDestination
import com.example.trainingapp.ui.home.HomeScreen

@Composable
fun TrainingNavigation(
    navController : NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToHistory = { navController.navigate(HistoryDestination.route) },
                navigateToHome = { /*DO NOTHING*/ },
                navigateToProfile = { /*TODO*/ }
            )
        }

        composable(route = HistoryDestination.route) {
            HistoryScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToProfile = { /*TODO*/ },
                navigateToHistory = { /*DO NOTHING*/ }
            )
        }
    }
}
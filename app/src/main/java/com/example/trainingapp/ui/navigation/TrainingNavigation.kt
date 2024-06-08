package com.example.trainingapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.H
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.ui.history.HistoryDestination
import com.example.trainingapp.ui.history.HistoryScreen
import com.example.trainingapp.ui.home.HomeDestination
import com.example.trainingapp.ui.home.HomeScreen
import com.example.trainingapp.ui.training.ExerciseEntryScreen
import com.example.trainingapp.ui.training.ExerciseEntryScreenDestination
import com.example.trainingapp.ui.training.TrainingEntryScreen
import com.example.trainingapp.ui.training.TrainingEntryScreenDestination

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
                navigateToProfile = { /*TODO*/ },
                navigateToTrainingEntry = {navController.navigate(TrainingEntryScreenDestination.route)}
            )
        }

        composable(route = HistoryDestination.route) {
            HistoryScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToProfile = { /*TODO*/ },
                navigateToHistory = { /*DO NOTHING*/ }
            )
        }

        composable(route = TrainingEntryScreenDestination.route) {
            TrainingEntryScreen(
                navigateBack = { navController.popBackStack() },
                navigateToExerciseEntry = { navController.navigate(ExerciseEntryScreenDestination.route) }
            )
        }

        composable(route = ExerciseEntryScreenDestination.route) {
            ExerciseEntryScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}
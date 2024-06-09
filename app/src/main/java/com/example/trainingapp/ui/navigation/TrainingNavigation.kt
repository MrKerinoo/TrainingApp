package com.example.trainingapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.H
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.ui.history.HistoryDestination
import com.example.trainingapp.ui.history.HistoryScreen
import com.example.trainingapp.ui.home.HomeDestination
import com.example.trainingapp.ui.home.HomeScreen
import com.example.trainingapp.ui.training.ExerciseEditDestination
import com.example.trainingapp.ui.training.ExerciseEditScreen
import com.example.trainingapp.ui.training.ExerciseEntryScreen
import com.example.trainingapp.ui.training.ExerciseEntryScreenDestination
import com.example.trainingapp.ui.training.TrainingEditDestination
import com.example.trainingapp.ui.training.TrainingEditScreen
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
                navigateToTrainingEntry = { navController.navigate(TrainingEntryScreenDestination.route) },
                navigateToTrainingEdit = {
                    navController.navigate("${TrainingEditDestination.route}/$it")}
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

        composable(
            route = ExerciseEntryScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(ExerciseEntryScreenDestination.trainingIdArg) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val trainingId = backStackEntry.arguments?.getInt(ExerciseEntryScreenDestination.trainingIdArg)
            ExerciseEntryScreen(
                navigateBack = { navController.popBackStack() },
                trainingId = trainingId // Pass the trainingId to the ExerciseEntryScreen
            )
        }

        composable(
            route = TrainingEditDestination.routeWithArgs,
            arguments = listOf(navArgument(TrainingEditDestination.trainingIdArg) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val trainingId = backStackEntry.arguments?.getInt(TrainingEditDestination.trainingIdArg)
            TrainingEditScreen(
                navigateBack = { navController.popBackStack() },
                navigateToExerciseEntry = {
                    navController.navigate("${ExerciseEntryScreenDestination.route}/$trainingId")
                },
                navigateToExerciseEdit = { exerciseId ->
                    navController.navigate("${ExerciseEditDestination.route}/$trainingId/$exerciseId")
                },
                navBackStackEntry = backStackEntry //ID training
            )
        }

        composable(
            route = ExerciseEditDestination.routeWithArgs,
            arguments = listOf(
                navArgument(ExerciseEditDestination.exerciseIdArg) {
                    type = NavType.IntType
                },
                navArgument(ExerciseEditDestination.trainingIdArg) {
                    type = NavType.IntType
                }
            )
        ) {
            ExerciseEditScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}
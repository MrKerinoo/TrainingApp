package com.example.trainingapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.trainingapp.ui.history.HistoryDestination
import com.example.trainingapp.ui.history.HistoryScreen
import com.example.trainingapp.ui.home.HomeDestination
import com.example.trainingapp.ui.home.HomeScreen
import com.example.trainingapp.ui.exercise.ExerciseEditDestination
import com.example.trainingapp.ui.exercise.ExerciseEditScreen
import com.example.trainingapp.ui.exercise.ExerciseEntryScreen
import com.example.trainingapp.ui.exercise.ExerciseEntryScreenDestination
import com.example.trainingapp.ui.profile.SettingsDestination
import com.example.trainingapp.ui.profile.SettingsScreen
import com.example.trainingapp.ui.profile.UserDestination
import com.example.trainingapp.ui.profile.UserScreen
import com.example.trainingapp.ui.training.TrainingEditDestination
import com.example.trainingapp.ui.training.TrainingEditScreen
import com.example.trainingapp.ui.training.TrainingEntryScreen
import com.example.trainingapp.ui.training.TrainingEntryScreenDestination
import com.example.trainingapp.ui.training.TrainingStartWorkoutDestination
import com.example.trainingapp.ui.training.TrainingStartWorkoutScreen

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
                navigateToProfile = { navController.navigate(UserDestination.route) },
                navigateToTrainingEntry = { navController.navigate(TrainingEntryScreenDestination.route) },
                navigateToTrainingEdit = {
                    navController.navigate("${TrainingEditDestination.route}/$it")},
                navigateToTrainingStartWorkout = {
                    navController.navigate("${TrainingStartWorkoutDestination.route}/$it")}
            )
        }

        composable(route = HistoryDestination.route) {
            HistoryScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToProfile = { navController.navigate(UserDestination.route) },
            )
        }

        composable(route = UserDestination.route) {
            UserScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToHistory = { navController.navigate(HistoryDestination.route) },
                navigateToSettings = { navController.navigate(SettingsDestination.route)}
            )
        }
        
        composable(route = SettingsDestination.route) {
            SettingsScreen(navigateBack = { navController.popBackStack() })
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
                trainingId = trainingId
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

        composable(
            route = TrainingStartWorkoutDestination.routeWithArgs,
            arguments = listOf(navArgument(TrainingEditDestination.trainingIdArg) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val trainingId = backStackEntry.arguments?.getInt(TrainingEditDestination.trainingIdArg)
            TrainingStartWorkoutScreen(
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
    }
}
package com.example.trainingapp.ui.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.example.trainingapp.R
import com.example.trainingapp.TrainingAppTopAppBar
import com.example.trainingapp.data.entities.Exercise
import com.example.trainingapp.ui.AppViewModelProvider
import com.example.trainingapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

object TrainingStartWorkoutDestination : NavigationDestination {
    override val route: String = "start_workout"
    override val titleRes: Int = R.string.workout
    const val trainingIdArg = "trainingId"
    val routeWithArgs = "${route}/{${trainingIdArg}}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingStartWorkoutScreen (
    navigateBack: () -> Unit,
    navigateToExerciseEntry: (Int) -> Unit,
    navigateToExerciseEdit: (Int) -> Unit,
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry,
    viewModel: TrainingEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val trainingUiState = viewModel.trainigUiState
    val exerercisesUiState by viewModel.exercisesUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val trainingId = navBackStackEntry.arguments?.getInt(TrainingEditDestination.trainingIdArg)

    Scaffold(
        topBar = {
            TrainingAppTopAppBar(
                title = stringResource(TrainingStartWorkoutDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            )
            {
                Button(
                    onClick = { coroutineScope.launch {
                        navigateBack()
                    } },
                    enabled = trainingUiState.isEntryValid,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onError
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_extra_large))
                ) {
                    Text(
                        text = stringResource(R.string.cancel_workout_action),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

        },
    ) { innerPadding ->
        TrainingStartWorkoutBody(
            exercisesList = exerercisesUiState.exerciseList,
            trainingId = trainingId!!,
            navigateToExerciseEntry =  { navigateToExerciseEntry(it) },
            navigateBack = navigateBack,
            updateTraining = {
                coroutineScope.launch {
                    viewModel.updateTraining()
                }
            },
            insertTrainingHistory = {
                coroutineScope.launch {
                    viewModel.insertTrainingHistory()
                }
            },
            onExerciseClick = navigateToExerciseEdit,
            onDeleteTraining = {
                coroutineScope.launch {
                    viewModel.deleteTraining()
                    navigateBack()
                } },
            onTrainingValueChange = viewModel::updateUiState,
            trainingUiState = trainingUiState,
            modifier = Modifier.padding(innerPadding)
        )

    }
}

@Composable
fun TrainingStartWorkoutBody(
    exercisesList: List<Exercise>,
    trainingId: Int,
    updateTraining : () -> Unit,
    insertTrainingHistory : () -> Unit,
    navigateToExerciseEntry : (Int) -> Unit,
    navigateBack: () -> Unit,
    onDeleteTraining : () -> Unit,
    onTrainingValueChange : (TrainingDetails) -> Unit,
    onExerciseClick : (Int) -> Unit,
    trainingUiState: TrainingUiState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    Column  (
        modifier = modifier
    ){
        TimerScreen()

        Button(
            onClick = { coroutineScope.launch {
                updateTraining()
                insertTrainingHistory()
                navigateBack()
            } },
            enabled = trainingUiState.isEntryValid,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_extra_large))
        ) {
            Text(
                text = stringResource(R.string.finish_training_action),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        TrainingEditBody(
            exercisesList = exercisesList,
            trainingId = trainingId,
            navigateToExerciseEntry = navigateToExerciseEntry,
            onDeleteTraining = onDeleteTraining,
            onTrainingValueChange = onTrainingValueChange,
            onExerciseClick = onExerciseClick,
            trainingUiState = trainingUiState,
            modifier = Modifier,
        )


    }

}


@Composable
fun TimerScreen() {
    var timePassed by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = "timer") {
        while (true) {
            delay(1000L)
            timePassed++
        }
    }

    val hours = timePassed / 3600
    val minutes = (timePassed % 3600) / 60
    val seconds = timePassed % 60

    val timeString = when
    {
        hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
        else -> String.format("%02d:%02d", minutes, seconds)
    }

    Column  (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .padding(vertical = dimensionResource(R.dimen.padding_small))
    ){
        Text (
            text = stringResource(R.string.timer),
            color = MaterialTheme.colorScheme.secondary
        )

        Text(
            text = timeString,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(horizontal = 2.dp)
        )
    }
}

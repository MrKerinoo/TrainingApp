package com.example.trainingapp.ui.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trainingapp.R
import com.example.trainingapp.TrainingAppTopAppBar
import com.example.trainingapp.ui.AppViewModelProvider
import com.example.trainingapp.ui.navigation.NavigationDestination
import com.example.trainingapp.ui.training.DialogBody
import kotlinx.coroutines.launch

object ExerciseEditDestination : NavigationDestination {
    override val route: String = "exercise_edit"
    override val titleRes: Int = R.string.exercise_edit
    const val trainingIdArg = "trainingId"
    const val exerciseIdArg = "exerciseId"
    val routeWithArgs = "$route/{$trainingIdArg}/{$exerciseIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExerciseEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{
    val coroutineScope = rememberCoroutineScope()
    val exerciseUiState = viewModel.exerciseUiState

    Scaffold (
        topBar = {
            TrainingAppTopAppBar(
                title = stringResource(ExerciseEditDestination.titleRes),
                canNavigateBack = false
            )
        },
        bottomBar = {
            Button(
                onClick = { coroutineScope.launch {
                    viewModel.updateExercise()
                    navigateBack()
                } },
                enabled = exerciseUiState.isEntryValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_extra_large))
            ) {
                Text(
                    text = stringResource(R.string.save_action),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        modifier = modifier
    ) {innerPadding ->
        ExerciseEditBody(
            onDeleteTraining = {
                coroutineScope.launch {
                    viewModel.deleteExercise()
                    navigateBack()
                } },
            exerciseUiState = exerciseUiState,
            onExerciseValueChange = viewModel::updateUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ExerciseEditBody(
    onDeleteTraining : () -> Unit,
    exerciseUiState: ExerciseUiState,
    onExerciseValueChange: (ExerciseDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        ExerciseEntryBody(
            exerciseUiState = exerciseUiState,
            onExerciseValueChange = onExerciseValueChange
        )

        val showDialog = remember { mutableStateOf(false) }

        Button(
            onClick = { showDialog.value = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onError,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_extra_large))
        )
        {
            Text(
                text = stringResource(R.string.delete_exercise_action),
                color = MaterialTheme.colorScheme.error
            )
        }

        DialogBody(
            onClickYes = onDeleteTraining,
            question = stringResource(R.string.delete_exercise_question),
            action = stringResource(R.string.delete_exercise_action),
            showDialog = showDialog
        )
    }
}
package com.example.trainingapp.ui.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trainingapp.R
import com.example.trainingapp.TrainingAppTopAppBar
import com.example.trainingapp.ui.AppViewModelProvider
import com.example.trainingapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

/**
 * ExerciseEntryScreenDestination is a NavigationDestination object that represents the destination
 * for the ExerciseEntryScreen composable.
 */
object ExerciseEntryScreenDestination : NavigationDestination {
    override val route: String = "exercise_entry"
    override val titleRes: Int = R.string.exercise_entry

    const val trainingIdArg = "trainingId"
    val routeWithArgs = "$route/{$trainingIdArg}"
}

/**
 * ExerciseEntryScreen is a composable function that displays the UI for
 * creating a new exercise entry.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEntryScreen(
    navigateBack: () -> Unit,
    trainingId: Int?,
    modifier: Modifier = Modifier,
    viewModel: ExerciseEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{
    val exerciseUiState = viewModel.exerciseUiState
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TrainingAppTopAppBar(
                title = stringResource(ExerciseEntryScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        bottomBar = {
            Button(
                onClick = { coroutineScope.launch {
                    viewModel.saveExercise(trainingId!!)
                    navigateBack()
                } },
                enabled = exerciseUiState.isNameValid && exerciseUiState.isSetsValid &&
                        exerciseUiState.isRepsValid && exerciseUiState.isWeightValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_extra_large))
            ) {
                Text(
                    text = stringResource(R.string.save_action),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        },
        modifier = modifier
    )
    {innerPadding ->
        Box (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ExerciseEntryBody(
                exerciseUiState = exerciseUiState,
                onExerciseValueChange = viewModel::updateUiState,
                modifier = Modifier
            )
        }
    }
}

/**
 * ExerciseEntryBody is a composable function that displays the body of the ExerciseEntryScreen.
 * It contains the form for entering exercise details.
 */
@Composable
fun ExerciseEntryBody(
    exerciseUiState: ExerciseUiState,
    onExerciseValueChange: (ExerciseDetails) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
    modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large))
    ){
        ExerciseEntryForm(
            exerciseUiState = exerciseUiState,
            onExerciseValueChange = onExerciseValueChange,
            modifier = Modifier)
    }

}

/**
 * ExerciseEntryForm is a composable function that displays the form for entering exercise details.
 * It contains the input fields for the exercise name, sets, reps, and weight.
 */
@Composable
fun ExerciseEntryForm(
    exerciseUiState: ExerciseUiState,
    onExerciseValueChange: (ExerciseDetails) -> Unit,
    modifier: Modifier = Modifier,
)
{
    Column (
    ) {
        OutlinedTextField(
            value = exerciseUiState.exerciseDetails.name,
            onValueChange = { newValue -> onExerciseValueChange(exerciseUiState.exerciseDetails.copy(name = newValue))
            },
            isError = !exerciseUiState.isNameValid,
            label = { Text(stringResource(R.string.exercise_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,

                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,

                disabledContainerColor = MaterialTheme.colorScheme.secondary,
            ),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            enabled = true,
            singleLine = true
        )

        OutlinedTextField(
            value = exerciseUiState.exerciseDetails.sets,
            onValueChange = { newValue -> onExerciseValueChange(exerciseUiState.exerciseDetails.copy(sets = newValue))
            },
            isError = !exerciseUiState.isSetsValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.exercise_sets_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,

                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,

                disabledContainerColor = MaterialTheme.colorScheme.secondary,
            ),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            enabled = true,
            singleLine = true
        )

        OutlinedTextField(
            value = exerciseUiState.exerciseDetails.reps,
            onValueChange = { newValue -> onExerciseValueChange(exerciseUiState.exerciseDetails.copy(reps = newValue))
            },
            isError = !exerciseUiState.isRepsValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.exercise_reps_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,

                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,

                disabledContainerColor = MaterialTheme.colorScheme.secondary,
            ),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            enabled = true,
            singleLine = true
        )

        OutlinedTextField(
            value = exerciseUiState.exerciseDetails.weight,
            onValueChange = { newValue -> onExerciseValueChange(exerciseUiState.exerciseDetails.copy(weight = newValue)) },
            isError = !exerciseUiState.isWeightValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.exercise_weight_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,

                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,

                disabledContainerColor = MaterialTheme.colorScheme.secondary,
            ),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            enabled = true,
            singleLine = true
        )
    }

}
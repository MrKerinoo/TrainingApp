package com.example.trainingapp.ui.training

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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

object ExerciseEntryScreenDestination : NavigationDestination {
    override val route: String = "exercise_entry"
    override val titleRes: Int = R.string.exercise_entry
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEntryScreen(
    navigateBack: () -> Unit,
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
                    viewModel.saveExercise()
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
        })
    {innerPadding ->
        ExerciseEntryBody(
            exerciseUiState = exerciseUiState,
            onExerciseValueChange = viewModel::updateUiState,
            modifier = Modifier.padding(innerPadding)
        )

    }
}

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
            onValueChange = { newValue -> onExerciseValueChange(exerciseUiState.exerciseDetails.copy(name = newValue))},
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
            value = exerciseUiState.exerciseDetails.sets.toString(),
            onValueChange = { newValue -> onExerciseValueChange(exerciseUiState.exerciseDetails.copy(sets = newValue.toInt()))},
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
            value = exerciseUiState.exerciseDetails.reps.toString(),
            onValueChange = { newValue -> onExerciseValueChange(exerciseUiState.exerciseDetails.copy(reps = newValue.toInt()))},
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
    }

}
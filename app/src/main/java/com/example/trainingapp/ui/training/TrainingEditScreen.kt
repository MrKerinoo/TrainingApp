package com.example.trainingapp.ui.training

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.example.trainingapp.R
import com.example.trainingapp.TrainingAppTopAppBar
import com.example.trainingapp.data.entities.Exercise
import com.example.trainingapp.ui.AppViewModelProvider
import com.example.trainingapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object TrainingEditDestination : NavigationDestination {
    override val route: String = "training_edit"
    override val titleRes: Int = R.string.training_edit
    const val trainingIdArg = "trainingId"
    val routeWithArgs = "${route}/{${trainingIdArg}}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingEditScreen(
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

    //TRAINING ID = PK
    val trainingId = navBackStackEntry.arguments?.getInt(TrainingEditDestination.trainingIdArg)

    Scaffold(
        topBar = {
            TrainingAppTopAppBar(
                title = stringResource(TrainingEditDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            Button(
                onClick = { coroutineScope.launch {
                    viewModel.updateTraining()
                    navigateBack()
                } },
                enabled = trainingUiState.isEntryValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_extra_large))
            ) {
                Text(
                    text = stringResource(R.string.save_action),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Box (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            TrainingEditBody(
                exercisesList = exerercisesUiState.exerciseList,
                trainingId = trainingId!!,
                navigateToExerciseEntry = { navigateToExerciseEntry(it) },
                onExerciseClick = navigateToExerciseEdit,
                onDeleteTraining = {
                    coroutineScope.launch {
                        viewModel.deleteTraining()
                        navigateBack()
                    }
                },
                onTrainingValueChange = viewModel::updateUiState,
                trainingUiState = trainingUiState,
                modifier = Modifier
            )
        }

    }
}

@Composable
fun TrainingEditBody(
    exercisesList: List<Exercise>,
    trainingId: Int,
    navigateToExerciseEntry : (Int) -> Unit,
    onDeleteTraining : () -> Unit,
    onTrainingValueChange : (TrainingDetails) -> Unit,
    onExerciseClick : (Int) -> Unit,
    trainingUiState: TrainingUiState,
    modifier: Modifier = Modifier
)
{
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues (0.dp)
    ){
        //Training entry
        item {
            TrainingEntryBody(
                trainingUiState = trainingUiState,
                onTrainingValueChange = onTrainingValueChange,
                modifier = Modifier
            )
        }

        //Exercises list
        items(exercisesList) { exercise ->
            ExerciseItem(
                exercise = exercise,
                modifier = Modifier
                    .clickable { onExerciseClick(exercise.id) }
                    .padding(dimensionResource(id = R.dimen.padding_large))
            )
        }

        //Add exercise
        item {
            Button(
                onClick = { navigateToExerciseEntry(trainingId) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onTertiary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 0.dp,
                        horizontal = dimensionResource(id = R.dimen.padding_extra_large)
                    )
            )
            {
                Text(
                    text = stringResource(R.string.add_exercise_action),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }


        //Delete training
        item {
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
                    text = stringResource(R.string.delete_training_action),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }

            DialogBody(
                question = stringResource(R.string.delete_training_question),
                action = stringResource(R.string.delete_training_action),
                showDialog = showDialog,
                onClickYes = onDeleteTraining
            )
        }
    }
}



@Composable
private fun InfoBody(
) {
    Row (
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_large))
    ){
        Text(
            text = stringResource(R.string.sets),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.weight(1f))

        Text (
            text = stringResource(R.string.reps),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = stringResource(R.string.weight),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun ExerciseItem (
    exercise: Exercise,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.tertiary)
    ){
        Text (
            text = exercise.name,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
        )

        InfoBody()

        Row (
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
        ){

            Text(
                text = exercise.sets.toString(),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.weight(1f))

            Text (
                text = exercise.reps.toString(),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = exercise.weight.toString(),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

}

@Composable
fun DialogBody(
    onClickYes : () -> Unit,
    question: String,
    action: String,
    showDialog: MutableState<Boolean>
)
{
    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            // Use a layout composable like Box, Column, or Row to arrange your dialog elements
            Box(
                modifier = Modifier
                    .size(300.dp, 200.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .border(1.dp, MaterialTheme.colorScheme.secondary)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_extra_large)),

                    ) {
                    Text(
                        text = action,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = question)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Button(
                            onClick = {
                                onClickYes()
                                showDialog.value = false
                            },
                            modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.secondary)
                        ) {
                            Text(stringResource(R.string.yes))
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = { showDialog.value = false },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .border(1.dp, MaterialTheme.colorScheme.secondary)
                        ) {
                            Text(stringResource(R.string.no))
                        }

                    }
                }
            }
        }
    }
}
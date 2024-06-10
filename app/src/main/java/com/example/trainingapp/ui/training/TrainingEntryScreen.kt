package com.example.trainingapp.ui.training

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trainingapp.R
import com.example.trainingapp.TrainingAppTopAppBar
import com.example.trainingapp.ui.AppViewModelProvider
import com.example.trainingapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object TrainingEntryScreenDestination : NavigationDestination {
    override val route: String = "training_entry"
    override val titleRes: Int = R.string.training_entry
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingEntryScreen(
    navigateBack: () -> Unit,
    navigateToExerciseEntry : () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TrainingEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val trainingUiState = viewModel.trainingUiState

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold (
        topBar = {
            TrainingAppTopAppBar(
                title = stringResource(TrainingEntryScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            Button(
                onClick = { coroutineScope.launch {
                    viewModel.saveTraining()
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
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
    ) { innerPadding ->
        TrainingEntryBody(
            trainingUiState = trainingUiState,
            onTrainingValueChange = viewModel::updateUiState,
            modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun TrainingEntryBody(
    trainingUiState: TrainingUiState,
    onTrainingValueChange: (TrainingDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_extra_large)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        TrainingInputForm(
            trainingUiState = trainingUiState,
            onTrainingValueChange = onTrainingValueChange,
            modifier = Modifier
        )
    }
}

@Composable
fun TrainingInputForm(
    trainingUiState: TrainingUiState,
    onTrainingValueChange: (TrainingDetails) -> Unit,
    modifier: Modifier = Modifier
)
{
    OutlinedTextField(
        value = trainingUiState.trainingDetails.name,
        onValueChange = { newValue -> onTrainingValueChange(trainingUiState.trainingDetails.copy(name = newValue))},
        label = { Text(stringResource(R.string.training_name_req)) },
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
            .fillMaxWidth(),
        enabled = true,
        singleLine = true
    )
}
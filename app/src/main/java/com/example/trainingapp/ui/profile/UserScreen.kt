package com.example.trainingapp.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trainingapp.R
import com.example.trainingapp.TrainingAppBottomAppBar
import com.example.trainingapp.TrainingAppTopAppBar
import com.example.trainingapp.ui.AppViewModelProvider
import com.example.trainingapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object UserDestination : NavigationDestination {
    override val route: String = "profile"
    override val titleRes: Int = R.string.profile
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    navigateToHome: ()  -> Unit,
    navigateToHistory: () -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val userUiState = viewModel.userUiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            TrainingAppTopAppBar(
                title = stringResource(UserDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onSettingsClick = navigateToSettings,
                showSettingsIcon = true
            )
        },
        bottomBar = {
            TrainingAppBottomAppBar(
                navigateToHome = navigateToHome,
                navigateToHistory = navigateToHistory,
                navigateToProfile = { /* DO NOTHING */ }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ProfileBody(
                userUiState = userUiState,
                onUserValueChange = { viewModel.updateUiState(it) },
                onSaveClick = {
                    if (userUiState.userDetails.id != 0)
                    {
                        coroutineScope.launch {
                            viewModel.updateUser() }
                    } else
                    {
                        coroutineScope.launch {
                            viewModel.saveUser()
                        }
                    }
                     },
                modifier = Modifier
            )
        }
    }
}

@Composable
fun ProfileBody (
    userUiState: UserUiState,
    onSaveClick: () -> Unit,
    onUserValueChange: (UserDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ){
        UserInputForm(
            userUiState = userUiState,
            onUserValueChange = onUserValueChange,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.padding_large))
        )

        Button(
            onClick = onSaveClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onTertiary,
            ),
            enabled = userUiState.isAgeValid && userUiState.isWeightValid && userUiState.isHeightValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_extra_large)
                )
        )
        {
            Text(
                text = stringResource(R.string.save_action),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
private fun UserInputForm(
    userUiState: UserUiState,
    onUserValueChange: (UserDetails) -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = userUiState.userDetails.name,
        onValueChange = { newValue -> onUserValueChange(userUiState.userDetails.copy(name = newValue))},
        label = { Text(stringResource(R.string.user_name_req)) },
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
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
        enabled = true,
        singleLine = true
    )

    OutlinedTextField(
        value = userUiState.userDetails.age,
        onValueChange = { newValue -> onUserValueChange(userUiState.userDetails.copy(age = newValue))},
        isError = !userUiState.isAgeValid,
        label = { Text(stringResource(R.string.user_age_req)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
        enabled = true,
        singleLine = true
    )

    OutlinedTextField(
        value = userUiState.userDetails.weight,
        onValueChange = { newValue -> onUserValueChange(userUiState.userDetails.copy(weight = newValue))},
        isError = !userUiState.isWeightValid,
        label = { Text(stringResource(R.string.user_weight_req)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
        enabled = true,
        singleLine = true
    )

    OutlinedTextField(
        value = userUiState.userDetails.height,
        onValueChange = { newValue -> onUserValueChange(userUiState.userDetails.copy(height = newValue))},
        isError = !userUiState.isHeightValid,
        label = { Text(stringResource(R.string.user_height_req)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
        enabled = true,
        singleLine = true
    )

}
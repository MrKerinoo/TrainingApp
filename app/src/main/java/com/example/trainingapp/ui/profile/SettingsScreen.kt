package com.example.trainingapp.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trainingapp.R
import com.example.trainingapp.TrainingAppTopAppBar
import com.example.trainingapp.ui.AppViewModelProvider
import com.example.trainingapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object SettingsDestination : NavigationDestination {
    override val route: String = "settings"
    override val titleRes: Int = R.string.settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val userUiState = viewModel.userUiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            TrainingAppTopAppBar(
                title = stringResource(SettingsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SettingsBody(
                userUiState = userUiState,
                updateUser = {
                    coroutineScope.launch {
                        viewModel.updateUser()
                    }
                },
                viewModel = viewModel,
                modifier = Modifier,
            )
        }
    }
}

@Composable
fun SettingsBody(
    userUiState: UserUiState,
    viewModel: UserViewModel,
    updateUser: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val options = listOf(stringResource(R.string.english_lang), stringResource(R.string.slovak_lang), stringResource(R.string.czech_lang))

    Row (
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_extra_large),
                vertical = dimensionResource(R.dimen.padding_large)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {

            Text(
                text = stringResource(R.string.language),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        bottom = 8.dp
                    )
            )

            options.forEach { option ->
                Row(Modifier.padding(vertical = 8.dp)) {
                    RadioButton(
                        selected = userUiState.userDetails.lang == option,
                        onClick = {
                            val updatedUserDetails = userUiState.userDetails.copy(lang = option)
                            viewModel.updateUiState(updatedUserDetails)
                            updateUser()
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.tertiary
                        )
                    )
                    Text(
                        text = option,
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                top = 11.dp
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(130.dp))

        Column {
            Text(
                text = stringResource(R.string.dark_mode),
                modifier = Modifier.padding(end = 8.dp)
            )
            Switch(
                checked = userUiState.userDetails.darkMode,
                onCheckedChange = { isChecked ->
                    val updatedUserDetails = userUiState.userDetails.copy(darkMode = isChecked)
                    viewModel.updateUiState(updatedUserDetails)
                    updateUser()
                },
            )
        }

    }
}
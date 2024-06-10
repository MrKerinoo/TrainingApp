package com.example.trainingapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.trainingapp.ui.navigation.TrainingNavigation

@Composable
fun TrainingApp(navController: NavHostController = rememberNavController()){
    TrainingNavigation(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingAppTopAppBar(
    title: String,
    canNavigateBack : Boolean,
    modifier : Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    showSettingsIcon: Boolean = false,
    onSettingsClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = FontWeight.Bold
        )
                },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = contentColorFor(MaterialTheme.colorScheme.primary)),
        modifier = modifier,
        actions = {
                  if (showSettingsIcon) {
                      IconButton(onClick = onSettingsClick) {
                          Icon(
                              imageVector = Icons.Filled.Settings,
                              contentDescription = stringResource(R.string.settings),
                              tint = MaterialTheme.colorScheme.tertiary
                          )
                      }
                  }
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(R.string.start_workout),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}
@Composable
fun TrainingAppBottomAppBar(
    navigateToHistory: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    navigateToProfile: () -> Unit = {}
) {

    Column {
        Divider(color = MaterialTheme.colorScheme.onPrimary)

        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = navigateToHistory) {
                        Icon(Filled.DateRange, contentDescription = "History")
                    }
                    Text(stringResource(R.string.history))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = navigateToHome) {
                        Icon(Filled.Add, contentDescription = "Start Workout")
                    }
                    Text(stringResource(R.string.start_workout))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = navigateToProfile) {
                        Icon(Filled.Person, contentDescription = "Profile")
                    }
                    Text(stringResource(R.string.profile))
                }
            }
        }
    }

}
package com.example.trainingapp.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trainingapp.R
import com.example.trainingapp.TrainingAppBottomAppBar
import com.example.trainingapp.TrainingAppTopAppBar
import com.example.trainingapp.data.entities.ExerciseHistory
import com.example.trainingapp.data.entities.TrainingHistory
import com.example.trainingapp.ui.AppViewModelProvider
import com.example.trainingapp.ui.navigation.NavigationDestination
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * HistoryDestination is a NavigationDestination object that represents the history screen.
 */
object HistoryDestination : NavigationDestination {
    override val route: String = "history"
    override val titleRes: Int = R.string.history
}

/**
 * HistoryScreen is a composable function that displays the history screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navigateToHome: () -> Unit,
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val historyUiState by viewModel.historyUiState.collectAsState()
    val exerciseHistoryUiState by viewModel.exerciseHistoryUiState.collectAsState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold (
        topBar = {
            TrainingAppTopAppBar(
                title = stringResource(HistoryDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            TrainingAppBottomAppBar(
                navigateToHistory = { /* DO NOTHING */  } ,
                navigateToHome = navigateToHome,
                navigateToProfile = navigateToProfile
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
            HistoryBody(
                trainingList = historyUiState.trainingList,
                exerciseHistoryList = exerciseHistoryUiState.exerciseHistoryList,
                modifier = Modifier
            )
        }
    }
}

/**
 * HistoryBody is a composable function that displays the body of the history screen.
 * It contains the list of training history.
 */
@Composable
private fun HistoryBody(
    trainingList: List<TrainingHistory>,
    exerciseHistoryList: List<ExerciseHistory>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (trainingList.isEmpty()) {
            Text (
                text = stringResource(R.string.no_history),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 30.sp,
                style = MaterialTheme.typography.titleLarge,
            )
        } else
        {
            HistoryList(
                trainingList = trainingList,
                exerciseHistoryList = exerciseHistoryList,
                modifier = Modifier
            )
        }

    }
}

/**
 * HistoryList is a composable function that displays the list of training history.
 * It contains the list of training history items.
 */
@Composable
private fun HistoryList(
    trainingList: List<TrainingHistory>,
    exerciseHistoryList: List<ExerciseHistory>,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier,
    ) {
        items(items = trainingList) {training ->
            HistoryItem(
                training = training,
                exerciseHistoryList = exerciseHistoryList,
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(id =R.dimen.padding_large),
                        horizontal = dimensionResource(id = R.dimen.padding_large))
            )
        }
    }
}

/**
 * HistoryItem is a composable function that displays a single training history item
 * with a list of information about exercises, name of training, date and duration
 * of training.
 */
@Composable
private fun HistoryItem(
    training: TrainingHistory,
    exerciseHistoryList: List<ExerciseHistory>,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
    val dateString = dateFormat.format(training.date)

    val hours = training.time / 3600
    val minutes = (training.time % 3600) / 60
    val seconds = (training.time % 3600) % 60

    val timeString = when {
        hours > 0 -> String.format("%dh%02dmin%02ds", hours, minutes, seconds)
        minutes > 0 -> String.format("%02dmin%02ds", minutes, seconds)
        else -> String.format("%02ds", seconds)
    }

    Column (
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.secondary)
            .padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_large)
            ),

    ) {

        Text(
            text = training.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(R.dimen.padding_small),
                    end = 25.dp
                )
        )

        Row (
            modifier = Modifier,
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "hah",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(
                        bottom = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_small)
                    )
            )

            Text(
                text = dateString,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.padding_small))
            )

            Spacer(Modifier.width(90.dp))

            Icon(
                painter = painterResource(R.drawable.timer),
                contentDescription = "ahaa",
                modifier = Modifier
                    .padding(
                        end = dimensionResource(R.dimen.padding_small)
                    )
            )

            Text (
                text = timeString,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.padding_small))
            )
        }

        InfoBody()


        // Exercise list
        exerciseHistoryList.forEach() { exercise ->
            if (training.id == exercise.trainingId) {
                ExerciseItem(
                    exercise = exercise
                )
            }
        }
    }
}

/**
 * ExerciseItem is a composable function that displays a single exercise item
 * with information about exercise name, number of sets, reps and weight.
 */
@Composable
private fun ExerciseItem(
    exercise: ExerciseHistory
) {
    Row {
        Box(
            modifier = Modifier
                .width(170.dp)
        ) {
            Text(
                text = exercise.name,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .width(49.dp)
        ) {
            Text(
                text = exercise.sets.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .width(55.dp)
        ) {
            Text(
                text = exercise.reps.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .width(80.dp)
        ) {
            Text(
                text = exercise.weight.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = "kg",
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}


/**
 * InfoBody is a composable function that displays the information about exercises
 * with the name of exercise, number of sets, reps and weight.
 */
@Composable
private fun InfoBody() {
    Row {
        Box(
            modifier = Modifier
                .width(160.dp)
                .padding(bottom = dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(R.string.exercise),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .width(50.dp)
        ) {
            Text(
                text = stringResource(R.string.sets),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .width(65.dp)
        ) {
            Text(
                text = stringResource(R.string.reps),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .width(80.dp)
        ) {
            Text(
                text = stringResource(R.string.weight),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
package com.example.trainingapp.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trainingapp.R
import com.example.trainingapp.TrainingAppBottomAppBar
import com.example.trainingapp.TrainingAppTopAppBar
import com.example.trainingapp.data.Training
import com.example.trainingapp.ui.AppViewModelProvider
import com.example.trainingapp.ui.home.HomeViewModel
import com.example.trainingapp.ui.navigation.NavigationDestination
import java.text.SimpleDateFormat
import java.time.Month
import java.util.Calendar
import java.util.Locale

object HistoryDestination : NavigationDestination {
    override val route: String = "history"
    override val titleRes: Int = R.string.history
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navigateToHistory : () -> Unit,
    navigateToHome: () -> Unit,
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val historyUiState by viewModel.historyUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TrainingAppTopAppBar(
                title = stringResource(HistoryDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateToHome
            )
        },
        bottomBar = {
            TrainingAppBottomAppBar(
                navigateToHistory = navigateToHistory,
                navigateToHome = navigateToHome,
                navigateToProfile = navigateToProfile
            )
        }
    ) { innerPadding ->
        HistoryBody(
            trainingList = historyUiState.trainingList,
            onItemClick = { /*TODO*/ },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HistoryBody(
    trainingList: List<Training>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentPadding = PaddingValues(16.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        DaysOfWeekRow(
            modifier = modifier
        )
        MonthsList(
            trainingList = trainingList,
            contentPadding = contentPadding,
            modifier = modifier
        )
    }
}

@Composable
private fun MonthsList(
    trainingList: List<Training>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.fillMaxHeight(),
        contentPadding = contentPadding
    ) {
        items(items = trainingList) {training ->
            TrainingCalendar(
                training = training,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun TrainingCalendar(
    training: Training,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val dateString = dateFormat.format(training.date)

    Text(
        text = dateString,
        modifier = modifier
    )
}

@Composable
private fun DaysOfWeekRow(modifier: Modifier = Modifier) {
    val daysOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier) {
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
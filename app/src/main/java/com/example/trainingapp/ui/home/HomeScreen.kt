package com.example.trainingapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trainingapp.R
import com.example.trainingapp.TrainingAppBottomAppBar
import com.example.trainingapp.TrainingAppTopAppBar
import com.example.trainingapp.data.Training
import com.example.trainingapp.ui.navigation.NavigationDestination
import com.example.trainingapp.ui.AppViewModelProvider
import java.text.SimpleDateFormat
import java.util.Locale
import javax.xml.transform.Templates

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.home
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToHistory: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToProfile: () ->Unit,
    modifier: Modifier = Modifier,
    viewModel : HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TrainingAppTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            TrainingAppBottomAppBar(
                navigateToHistory = navigateToHistory,
                navigateToHome = navigateToHome,
                navigateToProfile = navigateToProfile
            )
        },
    ) { innerPadding ->
        HomeBody(
            trainingList = homeUiState.trainingList,
            onItemClick = { /*TODO*/ },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeBody(
    trainingList: List<Training>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
)
{
    Column (
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Training(
            modifier = Modifier.padding(16.dp),
            addOnClick = { /*TODO*/ }
        )

        if (trainingList.isEmpty())
        {
            Text (
                text = stringResource(R.string.no_training),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                style = MaterialTheme.typography.titleLarge,
            )
        } else
        {
            TrainingList(
                trainingList = trainingList,
                onItemClick = {onItemClick(it.id)},
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun TrainingList(
    trainingList: List<Training>,
    onItemClick: (Training) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = "${stringResource(R.string.my_trainings)}(${trainingList.size})",
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
            )
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2))
        {
            items(trainingList) { training ->
                TrainingItem(training = training,
                    modifier = Modifier
                        .clickable {onItemClick(training)})
            }
        }
    }

}

@Composable
private fun TrainingItem(
    training: Training,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
    val dateString = dateFormat.format(training.date)

    Card (
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .border(1.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(25.dp)),
        shape = RoundedCornerShape(25.dp), // make corners rounded
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text (
                text = training.name,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                textAlign = TextAlign.Start,
            )
            Spacer(Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.calendar_title),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(Modifier.width(8.dp))
                Text (
                    text = dateString,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 14.sp)
                )
            }
        }
    }
}

@Composable
private fun Training(
  modifier: Modifier = Modifier,
  addOnClick: () -> Unit,
) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = stringResource(R.string.trainings),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Left,
            fontSize = 25.sp,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier
        )

        Row(
            modifier = modifier
                .clickable { addOnClick }
                .clip(RoundedCornerShape(25.dp))
                .background(color = MaterialTheme.colorScheme.onTertiary)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_training_title),
                tint = MaterialTheme.colorScheme.tertiary
            )

            Text(
                text = stringResource(R.string.training),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun TrainingPreview()
{
    Training(
        addOnClick = { /*TODO*/ }
    )
}
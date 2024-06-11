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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.ui.navigation.NavigationDestination
import com.example.trainingapp.ui.AppViewModelProvider
import com.example.trainingapp.ui.training.DialogBody
import java.text.SimpleDateFormat
import java.util.Locale

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.home
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToHistory: () -> Unit,
    navigateToProfile: () ->Unit,
    navigateToTrainingEntry: () -> Unit,
    navigateToTrainingEdit: (Int) -> Unit,
    navigateToTrainingStartWorkout: (Int) -> Unit,
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
                navigateToHome = { /* DO NOTHING */ },
                navigateToProfile = navigateToProfile
            )
        },
    ) { innerPadding ->

        Box (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HomeBody(
                trainingList = homeUiState.trainingList,
                onDeleteClick = { training -> viewModel.deleteTraining(training)},
                onTrainingClick = navigateToTrainingStartWorkout,
                onEditClick = navigateToTrainingEdit,
                addOnClick = navigateToTrainingEntry,
                modifier = Modifier
            )
        }

    }
}

@Composable
private fun HomeBody(
    onDeleteClick: (Training) -> Unit,
    trainingList: List<Training>,
    onEditClick: (Int) -> Unit,
    onTrainingClick: (Int) -> Unit,
    addOnClick: () -> Unit,
    modifier: Modifier = Modifier,
)
{
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Training(
            modifier = Modifier.padding(16.dp),
            addOnClick = addOnClick
        )

        if (trainingList.isEmpty())
        {
            Text (
                text = stringResource(R.string.no_training),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 30.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 223.dp)
            )
        } else
        {
            TrainingList(
                trainingList = trainingList,
                onEditClick = {onEditClick(it.id)},
                onTrainingClick = onTrainingClick,
                onDeleteClick = onDeleteClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun TrainingList(
    trainingList: List<Training>,
    onEditClick: (Training) -> Unit,
    onDeleteClick: (Training) -> Unit,
    onTrainingClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = "${stringResource(R.string.my_trainings)} (${trainingList.size})",
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
        )
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2))
        {
            items(trainingList) { training ->
                TrainingItem(
                    training = training,

                    onDeleteClick = {onDeleteClick(training)},
                    onEditClick = {onEditClick(training)},
                    onTrainingClick = {onTrainingClick(training.id)},
                    modifier = Modifier)
            }
        }
    }

}

@Composable
private fun TrainingItem(
    training: Training,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onTrainingClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val dateString: String

    if (training.date == null)
    {
        dateString = ""
    } else {
        val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
        dateString = dateFormat.format(training.date)
    }

    Card (
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(25.dp))
            .clickable(onClick = onTrainingClick),
        shape = RoundedCornerShape(25.dp),
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
            val expanded = remember { mutableStateOf(false) }

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text (
                    text = training.name,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Start,
                )

                Box (
                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .background(MaterialTheme.colorScheme.onTertiary)
                        .size(30.dp)
                ) {
                    IconButton(
                        onClick = { expanded.value = !expanded.value },
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(R.string.more_title),
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }

            }

            TrainingMenu(
                expanded = expanded,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
            )

            Spacer(Modifier.weight(1f))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.calendar),
                    contentDescription = stringResource(R.string.calendar_title),
                    tint = MaterialTheme.colorScheme.secondary
                )

                Spacer(Modifier.width(dimensionResource(R.dimen.padding_small)))

                Text(
                    text = dateString,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                )

            }
        }
    }
}
@Composable
private fun TrainingMenu(
    expanded: MutableState<Boolean>,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
)
{
    Box (
        modifier = Modifier
    ){
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.onSecondary)
                .border(2.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(25.dp)),
        ) {

            DropdownMenuItem(
                text = { Row {
                    Icon (
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.training_edit),
                        tint = MaterialTheme.colorScheme.tertiary
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = stringResource(R.string.training_edit),
                        color = MaterialTheme.colorScheme.onPrimary,

                        )
                } },
                onClick = onEditClick
            )


            val showDialog = remember { mutableStateOf(false) }

            DropdownMenuItem(
                text = { Row {
                    Icon (
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.delete_training_action),
                        tint = MaterialTheme.colorScheme.tertiary
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = stringResource(R.string.delete_training_action),
                        color = MaterialTheme.colorScheme.onPrimary,

                        )
                } },
                onClick =  { showDialog.value = true }
            )

            DialogBody(
                question = stringResource(R.string.delete_training_question),
                action = stringResource(R.string.delete_training_action),
                showDialog = showDialog,
                onClickYes = onDeleteClick
            )
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
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier
        )

        Box (
            modifier = Modifier
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(25.dp))
        ) {
            Button(
                onClick = addOnClick,
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_training_title),
                        tint = MaterialTheme.colorScheme.tertiary
                    )

                    Text(
                        text = stringResource(R.string.training),
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.W600,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
            )
        }

    }
}
package pl.kakol.kolejemazowieckie.presentation.trainsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TrainsListScreen(
    modifier: Modifier,
    viewModel: TrainsListViewModel
) {
    val trainsState = viewModel.trains.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 320.dp),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(trainsState.value) { train ->
            TrainItem(
                train = train,
                onSeenChange = { viewModel.handleSeenChange(train.id, it) },
                onRodeChange = { viewModel.handleRodeChange(train.id, it) },
                onRatingChange = { viewModel.handleRatingChange(train.id, it) }
            )
        }
    }
}
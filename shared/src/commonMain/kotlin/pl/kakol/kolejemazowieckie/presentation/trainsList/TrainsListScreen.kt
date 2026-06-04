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
import pl.kakol.kolejemazowieckie.presentation.theme.Dimens

@Composable
fun TrainsListScreen(
    modifier: Modifier,
    viewModel: TrainsListViewModel
) {
    val trainsState = viewModel.trains.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = Dimens.TrainBoxWidth),
        modifier = modifier,
        contentPadding = PaddingValues(Dimens.PaddingMedium),
        horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMedium),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(trainsState.value) { train ->
            TrainItem(
                train = train,
                onToggleCompletionButtonClick = {
                    viewModel.handleToggleCompletionButtonClick(it.id)
                }
            )
        }
    }
}
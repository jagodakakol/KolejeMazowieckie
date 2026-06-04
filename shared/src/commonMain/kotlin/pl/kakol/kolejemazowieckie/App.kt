package pl.kakol.kolejemazowieckie

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.kakol.kolejemazowieckie.presentation.trainsList.TrainsListScreen
import pl.kakol.kolejemazowieckie.presentation.trainsList.TrainsListViewModel

@Composable
fun App() {
    val viewModel: TrainsListViewModel = viewModel { TrainsListViewModel() }
    MaterialTheme {
        Scaffold { padding ->
            TrainsListScreen(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                viewModel = viewModel
            )
        }
    }
}
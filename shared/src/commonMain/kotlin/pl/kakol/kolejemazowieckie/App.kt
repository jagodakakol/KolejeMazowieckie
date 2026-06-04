package pl.kakol.kolejemazowieckie

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.kakol.kolejemazowieckie.presentation.theme.KmOrange
import pl.kakol.kolejemazowieckie.presentation.trainsList.TrainsListScreen
import pl.kakol.kolejemazowieckie.presentation.trainsList.TrainsListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val viewModel: TrainsListViewModel = viewModel { TrainsListViewModel() }
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Tabor Kolei Mazowieckich", fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = KmOrange,
                        titleContentColor = Color.White
                    )
                )
            }
        ) { padding ->
            TrainsListScreen(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                viewModel = viewModel
            )
        }
    }
}
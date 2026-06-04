package pl.kakol.kolejemazowieckie.presentation.trainsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.kakol.kolejemazowieckie.data.dataSource.TrainsRemoteDataSource
import pl.kakol.kolejemazowieckie.data.repository.TrainsRemoteRepository
import pl.kakol.kolejemazowieckie.domain.model.Train
import pl.kakol.kolejemazowieckie.domain.useCase.GetAllTrainsUseCase
import pl.kakol.kolejemazowieckie.domain.useCase.UpdateTrainUseCase

class TrainsListViewModel : ViewModel() {

    companion object {
        private const val STOP_TIMEOUT_MS = 5_000L
    }

    private val repository = TrainsRemoteRepository()
    private val getAllTrainsUseCase = GetAllTrainsUseCase(repository)
    private val updateTrainUseCase = UpdateTrainUseCase(repository)

    val trains: StateFlow<List<Train>> =
        getAllTrainsUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MS),
                initialValue = emptyList()
            )

    init {
        viewModelScope.launch {
            try {
                TrainsRemoteDataSource.refresh()
            } catch (e: Exception) {
                // brak połączenia z serwerem
            }
        }
    }

    fun handleSeenChange(id: String, seen: Boolean) {
        viewModelScope.launch { updateTrainUseCase(id, seen = seen) }
    }

    fun handleRodeChange(id: String, rode: Boolean) {
        viewModelScope.launch { updateTrainUseCase(id, rode = rode) }
    }

    fun handleRatingChange(id: String, rating: Int) {
        viewModelScope.launch { updateTrainUseCase(id, rating = rating) }
    }
}
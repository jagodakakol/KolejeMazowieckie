package pl.kakol.kolejemazowieckie.data.dataSource

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.kakol.kolejemazowieckie.domain.model.Train

object TrainsRemoteDataSource {

    private val _trains = MutableStateFlow<List<Train>>(emptyList())
    val trains: StateFlow<List<Train>> = _trains.asStateFlow()

    suspend fun refresh() {
        val result: List<Train> = HttpClientProvider.httpClient.get("/trains").body()
        _trains.value = result
    }

    fun toggleTrainCompletion(trainId: String) {
        _trains.update { list ->
            list.map { train ->
                if (train.id == trainId) {
                    train.copy(completed = !train.completed)
                } else {
                    train
                }
            }
        }
    }
}
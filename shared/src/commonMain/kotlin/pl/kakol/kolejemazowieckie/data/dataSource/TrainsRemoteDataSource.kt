package pl.kakol.kolejemazowieckie.data.dataSource

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.kakol.kolejemazowieckie.domain.model.Train
import pl.kakol.kolejemazowieckie.domain.model.TrainUpdateRequest

object TrainsRemoteDataSource {

    private val _trains = MutableStateFlow<List<Train>>(emptyList())
    val trains: StateFlow<List<Train>> = _trains.asStateFlow()

    suspend fun refresh() {
        val result: List<Train> = HttpClientProvider.httpClient.get("/trains").body()
        _trains.value = result
    }

    suspend fun updateTrain(
        id: String,
        seen: Boolean? = null,
        rode: Boolean? = null,
        rating: Int? = null
    ) {
        _trains.update { list ->
            list.map { train ->
                if (train.id == id) {
                    train.copy(
                        seen = seen ?: train.seen,
                        rode = rode ?: train.rode,
                        rating = rating ?: train.rating
                    )
                } else {
                    train
                }
            }
        }
        try {
            HttpClientProvider.httpClient.patch("/trains/$id") {
                contentType(ContentType.Application.Json)
                setBody(TrainUpdateRequest(seen = seen, rode = rode, rating = rating))
            }
        } catch (e: Exception) {
            // brak połączenia — zmiana zostaje lokalnie
        }
    }
}
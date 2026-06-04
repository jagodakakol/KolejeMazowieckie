package pl.kakol.kolejemazowieckie.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.kakol.kolejemazowieckie.domain.model.Train

interface TrainsRepository {
    fun getAllTrains(): Flow<List<Train>>
    suspend fun updateTrain(
        id: String,
        seen: Boolean? = null,
        rode: Boolean? = null,
        rating: Int? = null
    )
}
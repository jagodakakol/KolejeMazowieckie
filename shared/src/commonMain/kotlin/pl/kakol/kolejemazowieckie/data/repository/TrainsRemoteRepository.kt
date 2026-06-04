package pl.kakol.kolejemazowieckie.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kakol.kolejemazowieckie.data.dataSource.TrainsRemoteDataSource
import pl.kakol.kolejemazowieckie.domain.model.Train
import pl.kakol.kolejemazowieckie.domain.repository.TrainsRepository

class TrainsRemoteRepository : TrainsRepository {

    override fun getAllTrains(): Flow<List<Train>> =
        TrainsRemoteDataSource.trains

    override suspend fun updateTrain(id: String, seen: Boolean?, rode: Boolean?, rating: Int?) {
        TrainsRemoteDataSource.updateTrain(id, seen, rode, rating)
    }
}
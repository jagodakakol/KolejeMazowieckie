package pl.kakol.kolejemazowieckie.data.repository

import kotlinx.coroutines.flow.Flow
import pl.kakol.kolejemazowieckie.data.dataSource.TrainsLocalDataSource
import pl.kakol.kolejemazowieckie.domain.model.Train
import pl.kakol.kolejemazowieckie.domain.repository.TrainsRepository

class TrainsLocalRepository : TrainsRepository {

    override fun getAllTrains(): Flow<List<Train>> =
        TrainsLocalDataSource.trains

    override suspend fun toggleTrainCompletion(trainId: String) {
        TrainsLocalDataSource.toggleTrainCompletion(trainId)
    }
}
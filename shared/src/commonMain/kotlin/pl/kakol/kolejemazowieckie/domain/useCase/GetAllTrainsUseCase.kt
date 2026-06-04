package pl.kakol.kolejemazowieckie.domain.useCase

import kotlinx.coroutines.flow.Flow
import pl.kakol.kolejemazowieckie.domain.model.Train
import pl.kakol.kolejemazowieckie.domain.repository.TrainsRepository

class GetAllTrainsUseCase(
    private val repository: TrainsRepository
) {
    operator fun invoke(): Flow<List<Train>> = repository.getAllTrains()
}
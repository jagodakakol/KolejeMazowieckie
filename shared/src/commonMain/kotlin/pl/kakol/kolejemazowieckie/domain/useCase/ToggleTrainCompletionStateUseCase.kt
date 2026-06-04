package pl.kakol.kolejemazowieckie.domain.useCase

import pl.kakol.kolejemazowieckie.domain.repository.TrainsRepository

class ToggleTrainCompletionStateUseCase(
    private val repository: TrainsRepository
) {
    suspend operator fun invoke(trainId: String) {
        repository.toggleTrainCompletion(trainId)
    }
}
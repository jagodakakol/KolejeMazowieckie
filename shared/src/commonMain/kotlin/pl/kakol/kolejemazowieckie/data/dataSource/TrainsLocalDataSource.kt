package pl.kakol.kolejemazowieckie.data.dataSource

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.kakol.kolejemazowieckie.domain.model.Train

object TrainsLocalDataSource {

    private val _trains = MutableStateFlow(
        listOf(
            Train(
                id = "en57akm",
                name = "EN57AKM",
                infoUrl = "https://pl.wikipedia.org/wiki/EN57"
            ),
            Train(
                id = "en71",
                name = "EN71",
                infoUrl = "https://pl.wikipedia.org/wiki/EN71"
            ),
            Train(
                id = "en76_elf",
                name = "EN76 Elf",
                infoUrl = "https://pl.wikipedia.org/wiki/Pesa_Elf"
            ),
            Train(
                id = "er75_flirt",
                name = "ER75 FLIRT",
                infoUrl = "https://pl.wikipedia.org/wiki/Stadler_FLIRT",
                completed = true
            ),
            Train(
                id = "er160_flirt3",
                name = "ER160 FLIRT3",
                infoUrl = "https://pl.wikipedia.org/wiki/Stadler_FLIRT"
            ),
            Train(
                id = "ezt_45we",
                name = "45WE",
                infoUrl = "https://pl.wikipedia.org/wiki/Newag_Impuls"
            ),
            Train(
                id = "eu47_hetman",
                name = "EU47 Hetman",
                infoUrl = "https://pl.wikipedia.org/wiki/Bombardier_TRAXX",
                completed = true
            ),
            Train(
                id = "twindexx",
                name = "Wagony piętrowe Twindexx",
                infoUrl = "https://pl.wikipedia.org/wiki/Bombardier_Twindexx"
            )
        )
    )

    val trains: StateFlow<List<Train>> = _trains.asStateFlow()

    fun toggleTrainCompletion(trainId: String) {
        _trains.update { trainsList ->
            trainsList.map { train ->
                if (train.id == trainId) {
                    train.copy(completed = !train.completed)
                } else {
                    train
                }
            }
        }
    }
}
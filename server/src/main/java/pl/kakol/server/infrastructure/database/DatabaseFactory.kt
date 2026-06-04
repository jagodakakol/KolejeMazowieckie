package pl.kakol.server.infrastructure.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import pl.kakol.server.domain.model.Train

object DatabaseFactory {

    fun init() {
        Database.connect(
            url = "jdbc:h2:mem:trains;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
        transaction {
            SchemaUtils.create(TrainsTable)
            if (TrainsTable.selectAll().empty()) {
                seed()
            }
        }
    }

    private fun seed() {
        val trains = listOf(
            Train("en57akm", "EN57AKM", "https://pl.wikipedia.org/wiki/EN57", seen = true, rode = true, rating = 4),
            Train("en71", "EN71", "https://pl.wikipedia.org/wiki/EN71", seen = true, rating = 3),
            Train("en76_elf", "EN76 Elf", "https://pl.wikipedia.org/wiki/Pesa_Elf", seen = true, rode = true, rating = 5),
            Train("er75_flirt", "ER75 FLIRT", "https://pl.wikipedia.org/wiki/Stadler_FLIRT", seen = true, rode = true, rating = 5),
            Train("er160_flirt3", "ER160 FLIRT3", "https://pl.wikipedia.org/wiki/Stadler_FLIRT"),
            Train("ezt_45we", "45WE", "https://pl.wikipedia.org/wiki/Newag_Impuls", seen = true, rating = 4),
            Train("eu47_hetman", "EU47 Hetman", "https://pl.wikipedia.org/wiki/Bombardier_TRAXX", seen = true, rode = true, rating = 5),
            Train("twindexx", "Wagony piętrowe Twindexx", "https://pl.wikipedia.org/wiki/Bombardier_Twindexx", seen = true, rating = 4)
        )
        trains.forEach { train ->
            TrainsTable.insert {
                it[id] = train.id
                it[name] = train.name
                it[infoUrl] = train.infoUrl
                it[seen] = train.seen
                it[rode] = train.rode
                it[rating] = train.rating
            }
        }
    }
}
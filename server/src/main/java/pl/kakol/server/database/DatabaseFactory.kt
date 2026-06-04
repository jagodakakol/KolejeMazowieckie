package pl.kakol.server.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import pl.kakol.server.model.Train

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
            Train("en57akm", "EN57AKM", "https://pl.wikipedia.org/wiki/EN57"),
            Train("en71", "EN71", "https://pl.wikipedia.org/wiki/EN71"),
            Train("en76_elf", "EN76 Elf", "https://pl.wikipedia.org/wiki/Pesa_Elf"),
            Train("er75_flirt", "ER75 FLIRT", "https://pl.wikipedia.org/wiki/Stadler_FLIRT", completed = true),
            Train("er160_flirt3", "ER160 FLIRT3", "https://pl.wikipedia.org/wiki/Stadler_FLIRT"),
            Train("ezt_45we", "45WE", "https://pl.wikipedia.org/wiki/Newag_Impuls"),
            Train("eu47_hetman", "EU47 Hetman", "https://pl.wikipedia.org/wiki/Bombardier_TRAXX", completed = true),
            Train("twindexx", "Wagony piętrowe Twindexx", "https://pl.wikipedia.org/wiki/Bombardier_Twindexx")
        )
        trains.forEach { train ->
            TrainsTable.insert {
                it[id] = train.id
                it[name] = train.name
                it[infoUrl] = train.infoUrl
                it[completed] = train.completed
            }
        }
    }
}
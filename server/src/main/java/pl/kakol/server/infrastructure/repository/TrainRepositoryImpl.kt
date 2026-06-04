package pl.kakol.server.infrastructure.repository

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import pl.kakol.server.domain.model.Train
import pl.kakol.server.domain.repository.TrainRepository
import pl.kakol.server.infrastructure.database.TrainsTable

class TrainRepositoryImpl : TrainRepository {

    private fun ResultRow.toTrain() = Train(
        id = this[TrainsTable.id],
        name = this[TrainsTable.name],
        infoUrl = this[TrainsTable.infoUrl],
        seen = this[TrainsTable.seen],
        rode = this[TrainsTable.rode],
        rating = this[TrainsTable.rating]
    )

    override fun getAll(): List<Train> = transaction {
        TrainsTable.selectAll().map { it.toTrain() }
    }

    override fun getById(id: String): Train? = transaction {
        TrainsTable.selectAll().where { TrainsTable.id eq id }
            .map { it.toTrain() }
            .singleOrNull()
    }

    override fun add(train: Train) {
        transaction {
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

    override fun update(
        id: String,
        newName: String?,
        newInfoUrl: String?,
        newSeen: Boolean?,
        newRode: Boolean?,
        newRating: Int?
    ): Boolean = transaction {
        val rows = TrainsTable.update({ TrainsTable.id eq id }) { st ->
            if (newName != null) st[TrainsTable.name] = newName
            if (newInfoUrl != null) st[TrainsTable.infoUrl] = newInfoUrl
            if (newSeen != null) st[TrainsTable.seen] = newSeen
            if (newRode != null) st[TrainsTable.rode] = newRode
            if (newRating != null) st[TrainsTable.rating] = newRating
        }
        rows > 0
    }

    override fun delete(id: String): Boolean = transaction {
        TrainsTable.deleteWhere { it.run { TrainsTable.id eq id } } > 0
    }
}
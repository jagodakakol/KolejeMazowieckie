package pl.kakol.server.infrastructure.database

import org.jetbrains.exposed.sql.Table

object TrainsTable : Table("trains") {
    val id = varchar("id", 50)
    val name = varchar("name", 200)
    val infoUrl = varchar("info_url", 500)
    val seen = bool("seen")
    val rode = bool("rode")
    val rating = integer("rating")

    override val primaryKey = PrimaryKey(id)
}
package pl.kakol.server

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import pl.kakol.server.database.DatabaseFactory
import pl.kakol.server.database.TrainsTable
import pl.kakol.server.model.Train
import pl.kakol.server.model.TrainUpdateRequest

class ValidationException(message: String) : Exception(message)

private fun ResultRow.toTrain() = Train(
    id = this[TrainsTable.id],
    name = this[TrainsTable.name],
    infoUrl = this[TrainsTable.infoUrl],
    completed = this[TrainsTable.completed]
)

private fun allTrains(): List<Train> = transaction {
    TrainsTable.selectAll().map { it.toTrain() }
}

private fun getTrainById(trainId: String): Train? = transaction {
    TrainsTable.selectAll().where { TrainsTable.id eq trainId }
        .map { it.toTrain() }
        .singleOrNull()
}

private fun addTrain(train: Train) = transaction {
    TrainsTable.insert {
        it[id] = train.id
        it[name] = train.name
        it[infoUrl] = train.infoUrl
        it[completed] = train.completed
    }
}

private fun updateTrain(trainId: String, request: TrainUpdateRequest): Boolean = transaction {
    val updatedRows = TrainsTable.update({ TrainsTable.id eq trainId }) { statement ->
        request.name?.let { statement[name] = it }
        request.infoUrl?.let { statement[infoUrl] = it }
        request.completed?.let { statement[completed] = it }
    }
    updatedRows > 0
}

private fun deleteTrain(trainId: String): Boolean = transaction {
    TrainsTable.deleteWhere { it.run { TrainsTable.id eq trainId } } > 0
}

fun main() {
    DatabaseFactory.init()
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        install(StatusPages) {
            exception<ValidationException> { call, cause ->
                call.respond(HttpStatusCode.UnprocessableEntity, cause.message ?: "Błąd walidacji")
            }
            exception<BadRequestException> { call, _ ->
                call.respond(HttpStatusCode.BadRequest, "Nieprawidłowe dane lub zły JSON")
            }
            exception<Throwable> { call, _ ->
                call.respond(HttpStatusCode.InternalServerError, "Wewnętrzny błąd serwera")
            }
        }
        routing {
            get("/") {
                call.respondText("API Kolei Mazowieckich działa!")
            }

            get("/trains") {
                call.respond(allTrains())
            }

            get("/trains/{id}") {
                val id = call.parameters["id"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, "Brak id")
                val train = getTrainById(id)
                if (train != null) {
                    call.respond(train)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Nie znaleziono pociągu")
                }
            }

            post("/trains") {
                val train = call.receive<Train>()
                if (train.id.isBlank() || train.name.isBlank()) {
                    throw ValidationException("Pola 'id' oraz 'name' są wymagane")
                }
                addTrain(train)
                call.respond(HttpStatusCode.Created, "Pociąg dodany")
            }

            patch("/trains/{id}") {
                val id = call.parameters["id"]
                    ?: return@patch call.respond(HttpStatusCode.BadRequest, "Brak id")
                val request = call.receive<TrainUpdateRequest>()
                val updated = updateTrain(id, request)
                if (updated) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Nie znaleziono pociągu")
                }
            }

            delete("/trains/{id}") {
                val id = call.parameters["id"]
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, "Brak id")
                val deleted = deleteTrain(id)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Pociąg usunięty")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Nie znaleziono pociągu")
                }
            }
        }
    }.start(wait = true)
}
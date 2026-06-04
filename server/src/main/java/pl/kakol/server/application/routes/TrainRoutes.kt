package pl.kakol.server.application.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import pl.kakol.server.ValidationException
import pl.kakol.server.application.dto.TrainUpdateRequest
import pl.kakol.server.domain.model.Train
import pl.kakol.server.domain.repository.TrainRepository

fun Route.trainRoutes(repository: TrainRepository) {

    get("/") {
        call.respondText("API Kolei Mazowieckich działa!")
    }

    get("/trains") {
        call.respond(repository.getAll())
    }

    get("/trains/{id}") {
        val id = call.parameters["id"]
            ?: return@get call.respond(HttpStatusCode.BadRequest, "Brak id")
        val train = repository.getById(id)
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
        repository.add(train)
        call.respond(HttpStatusCode.Created, "Pociąg dodany")
    }

    patch("/trains/{id}") {
        val id = call.parameters["id"]
            ?: return@patch call.respond(HttpStatusCode.BadRequest, "Brak id")
        val request = call.receive<TrainUpdateRequest>()
        val updated = repository.update(id, request.name, request.infoUrl, request.completed)
        if (updated) {
            call.respond(HttpStatusCode.NoContent)
        } else {
            call.respond(HttpStatusCode.NotFound, "Nie znaleziono pociągu")
        }
    }

    delete("/trains/{id}") {
        val id = call.parameters["id"]
            ?: return@delete call.respond(HttpStatusCode.BadRequest, "Brak id")
        val deleted = repository.delete(id)
        if (deleted) {
            call.respond(HttpStatusCode.OK, "Pociąg usunięty")
        } else {
            call.respond(HttpStatusCode.NotFound, "Nie znaleziono pociągu")
        }
    }
}
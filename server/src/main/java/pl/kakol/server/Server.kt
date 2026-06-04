package pl.kakol.server

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import pl.kakol.server.application.routes.trainRoutes
import pl.kakol.server.domain.repository.TrainRepository
import pl.kakol.server.infrastructure.database.DatabaseFactory
import pl.kakol.server.infrastructure.repository.TrainRepositoryImpl

class ValidationException(message: String) : Exception(message)

fun main() {
    DatabaseFactory.init()

    embeddedServer(Netty, port = 8080) {
        dependencies {
            provide<TrainRepository> { TrainRepositoryImpl() }
        }

        val trainRepository: TrainRepository by dependencies

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
            trainRoutes(trainRepository)
        }
    }.start(wait = true)
}
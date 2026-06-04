package pl.kakol.kolejemazowieckie.data.dataSource

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import pl.kakol.kolejemazowieckie.getPlatform

val HOST = if (getPlatform().name.contains("Android")) "10.0.2.2" else "localhost"
const val PORT = 8080

object HttpClientProvider {

    val httpClient: HttpClient by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = HOST
                    port = PORT
                }
            }
        }
    }
}
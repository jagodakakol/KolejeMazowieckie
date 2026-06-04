package pl.kakol.server.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Train(
    val id: String,
    val name: String,
    val infoUrl: String,
    val seen: Boolean = false,
    val rode: Boolean = false,
    val rating: Int = 0
)
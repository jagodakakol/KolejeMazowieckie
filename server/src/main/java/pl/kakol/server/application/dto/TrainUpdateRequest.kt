package pl.kakol.server.application.dto

import kotlinx.serialization.Serializable

@Serializable
data class TrainUpdateRequest(
    val name: String? = null,
    val infoUrl: String? = null,
    val seen: Boolean? = null,
    val rode: Boolean? = null,
    val rating: Int? = null
)
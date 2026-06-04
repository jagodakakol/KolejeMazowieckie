package pl.kakol.server.model

import kotlinx.serialization.Serializable

@Serializable
data class TrainUpdateRequest(
    val name: String? = null,
    val infoUrl: String? = null,
    val completed: Boolean? = null
)
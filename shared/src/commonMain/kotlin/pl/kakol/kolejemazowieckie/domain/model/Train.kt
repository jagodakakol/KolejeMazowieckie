package pl.kakol.kolejemazowieckie.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Train(
    val id: String,
    val name: String,
    val infoUrl: String,
    val completed: Boolean = false
)
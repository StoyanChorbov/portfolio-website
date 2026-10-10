package model

import kotlinx.serialization.Serializable

@Serializable
data class ProjectDTO(
    val name: String? = null,
    val description: String? = null,
    val url: String? = null,
    val imageUrl: String? = null,
) {
}
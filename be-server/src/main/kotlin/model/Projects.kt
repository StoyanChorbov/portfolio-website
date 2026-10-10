package model

import org.jetbrains.exposed.v1.core.dao.id.UuidTable

object Projects : UuidTable(name = "projects") {
    val name = varchar("name", length = 72)
    val description = varchar("description", length = 255)
    val url = varchar("url", length = 255).nullable()
    val imageUrl = varchar("image_url", length = 255).nullable()
}
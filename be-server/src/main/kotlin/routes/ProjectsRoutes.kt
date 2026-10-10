package routes

import exception.InvalidTypeException
import exception.NullStringPropertyException
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.cio.*
import io.ktor.util.reflect.TypeInfo
import io.ktor.utils.io.*
import model.ProjectDTO
import org.koin.ktor.ext.inject
import service.ProjectsService
import java.io.File
import kotlin.reflect.typeOf
import kotlin.uuid.Uuid

fun Route.projectsRoutes() {
    val projectsService by inject<ProjectsService>()

    route("/projects") {
        get {
            val projects = projectsService.getAll()
            call.respond(HttpStatusCode.OK, projects)
        }

        get("/{id}") {
            val id = call.parameters["id"].toUuid()
            val project = projectsService.getProject(id)
            call.respond(project!!)
        }

        post {
            val result = handleFileInput(call.receiveMultipart())
            val id = projectsService.createProject(result.first, result.second)
            call.respond(HttpStatusCode.Created, "Id: $id")
        }

        patch("/{id}") {
            val id = call.parameters["id"].toUuid()
            val result = handleFileInput(call.receiveMultipart())
            projectsService.updateProject(id, result.first, result.second)
            call.respond(HttpStatusCode.OK, "Id: $id")
        }
    }
}

private fun String?.toUuid(): Uuid {
    if (this == null) {
        throw InvalidTypeException("Id is null")
    }
    val uuid = Uuid.parseOrNull(this) ?: throw InvalidTypeException("Invalid parameter type")
    return uuid
}

private suspend fun handleFileInput(data: MultiPartData): Pair<ProjectDTO, File?> {
    var name: String? = null
    var description: String? = null
    var url: String? = null
    var image: File? = null
    data.forEachPart { part ->
        when (part) {
            is PartData.FormItem -> {
                when (part.name) {
                    "name" -> name = part.value
                    "description" -> description = part.value
                    "url" -> url = part.value
                    "image" -> {} // image is handled below
                    else -> throw InvalidTypeException("Only name, description, url, and image are valid properties")
                }
            }

            is PartData.FileItem -> {
                val fileName = part.originalFileName ?: throw Exception("File name is null")
                image = File(fileName)
                part.provider().copyAndClose(image.writeChannel())
            }

            else -> {
                throw InvalidTypeException("Invalid item type: ${part.name}")
            }
        }
        part.release()
    }
    if (name == null) {
        throw NullStringPropertyException("Name is null")
    }
    if (description == null) {
        throw NullStringPropertyException("Description is null")
    }
    return Pair(ProjectDTO(name, description, url), image)
}
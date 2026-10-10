package config

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import routes.projectsRoutes

fun Application.configureRouting() {
    routing {
        projectsRoutes()
        get("/") {
            println("GET /")
            call.respondText("Hello, World!")
        }
    }
}
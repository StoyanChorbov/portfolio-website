package config

import exception.BaseException
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BaseException> { call, exception ->
            call.respondText(text = "Error: ${exception.message}" , status = exception.statusCode)
        }
    }
}

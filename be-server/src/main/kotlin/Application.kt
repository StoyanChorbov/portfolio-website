import config.configureDatabase
import config.configureHttp
import config.configureKoin
import config.configureResources
import config.configureRouting
import config.configureSerialization
import config.configureStatusPages
import io.ktor.server.application.Application

suspend fun Application.rootModule() {
    configureHttp()
    configureSerialization()
    configureKoin()
    configureResources()
    configureStatusPages()
    configureRouting()
    configureDatabase()
}
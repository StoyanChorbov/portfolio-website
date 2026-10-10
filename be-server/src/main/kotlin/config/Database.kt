package config

import io.ktor.server.application.Application
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.koin.ktor.ext.inject
import model.Projects

suspend fun Application.configureDatabase() {
    val database by inject<Database>()
    suspendTransaction(database) {
        SchemaUtils.create(Projects)
    }
}
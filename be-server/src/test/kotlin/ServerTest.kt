package eu.schorbov

import config.appModule
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import org.koin.dsl.module
import rootModule
import kotlin.test.*

class ServerTest {

    @Test
    fun `test root endpoint`() = testApplication {
        application {
            rootModule()
        }
        // loads default configuration
//        configure()

        val response = client.get("/")

        println("Status: ${response.status}")
        println("Body: ${response.bodyAsText()}")

        assertEquals(HttpStatusCode.OK, response.status)
        // verify server root returns 200
        assertEquals(HttpStatusCode.OK, client.get("/").status)
    }

}

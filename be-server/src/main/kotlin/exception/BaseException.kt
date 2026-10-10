package exception

import io.ktor.http.HttpStatusCode

open class BaseException(override val message: String?, val statusCode: HttpStatusCode) : Throwable(message)
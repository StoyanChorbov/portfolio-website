package exception

import io.ktor.http.HttpStatusCode

class InvalidTypeException(override val message: String?)
    : BaseException(message = message, statusCode = HttpStatusCode.BadRequest)
package exception

import io.ktor.http.HttpStatusCode

class NullStringPropertyException(override val message: String?)
    : BaseException(message = message, statusCode = HttpStatusCode.BadRequest)
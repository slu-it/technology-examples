package example

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.time.Clock
import java.time.Instant
import javax.servlet.http.HttpServletRequest

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val timestamp: Instant,
    val status: Int,
    val path: String,
    val error: String,
    val message: String?,
    val details: List<String>? = null
)

fun errorResponse(
    clock: Clock,
    request: HttpServletRequest,
    status: HttpStatus,
    message: String?,
    details: List<String>? = null
) = ErrorResponse(
    timestamp = clock.instant(),
    status = status.value(),
    error = status.reasonPhrase,
    path = request.requestURI,
    message = message,
    details = details
)

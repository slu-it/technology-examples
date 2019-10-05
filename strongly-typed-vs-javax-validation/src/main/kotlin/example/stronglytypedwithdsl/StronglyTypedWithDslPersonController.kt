package example.stronglytypedwithdsl

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException
import example.ErrorResponse
import example.errorResponse
import example.stronglytypedwithdsl.model.Person
import example.stronglytypedwithdsl.validation.ValidationException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Clock
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/stronglytypedwithdsl/persons")
class StronglyTypedWithDslPersonController(
    private val clock: Clock
) {

    @PostMapping
    fun addPerson(@RequestBody person: Person): Person {
        return person.copy(id = UUID.randomUUID())
    }

    @ExceptionHandler(InvalidDefinitionException::class)
    fun invalidDefinitionException(
        e: InvalidDefinitionException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> =
        when (val cause = e.cause) {
            is ValidationException -> status(BAD_REQUEST).body(
                errorResponse(
                    clock = clock,
                    status = BAD_REQUEST,
                    request = request,
                    message = "Payload validation failed!",
                    details = cause.problems
                )
            )
            else -> throw IllegalStateException("Should not happen ..", e)
        }

}

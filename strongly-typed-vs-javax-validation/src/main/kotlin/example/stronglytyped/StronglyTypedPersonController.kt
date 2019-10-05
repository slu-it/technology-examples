package example.stronglytyped

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException
import example.ErrorResponse
import example.errorResponse
import example.stronglytyped.model.Person
import example.stronglytyped.validation.ValidationException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Clock
import java.util.*
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/stronglytyped/persons")
class StronglyTypedPersonController(
    private val clock: Clock
) {

    private val database: MutableMap<UUID, Person> = mutableMapOf()

    @PostMapping
    fun addPerson(@RequestBody person: Person): Person {
        val id = UUID.randomUUID()
        val persistedPerson = person.copy(id = id)
        database.put(id, persistedPerson)
        return persistedPerson
    }

    @GetMapping
    fun getAllPersons(): List<Person> {
        return database.values.toList()
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
                    message = cause.message
                )
            )
            else -> status(INTERNAL_SERVER_ERROR).body(
                errorResponse(
                    clock = clock,
                    status = INTERNAL_SERVER_ERROR,
                    request = request,
                    message = e.message
                )
            )
        }

}

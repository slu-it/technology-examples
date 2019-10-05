package example.javaxvalidation

import example.javaxvalidation.model.Person
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/javaxvalidation/persons")
class JavaxValidationPersonController {

    private val database: MutableMap<UUID, Person> = mutableMapOf()

    @PostMapping
    fun addPerson(@Valid @RequestBody person: Person): Person {
        val id = UUID.randomUUID()
        val persistedPerson = person.copy(id = id)
        database.put(id, persistedPerson)
        return persistedPerson
    }

    @GetMapping
    fun getAllPersons(): List<Person> {
        return database.values.toList()
    }

}

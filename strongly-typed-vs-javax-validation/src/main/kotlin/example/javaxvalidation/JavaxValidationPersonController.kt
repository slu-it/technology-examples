package example.javaxvalidation

import example.javaxvalidation.model.Person
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/javaxvalidation/persons")
class JavaxValidationPersonController {

    @PostMapping
    fun addPerson(@Valid @RequestBody person: Person): Person {
        return person.copy(id = UUID.randomUUID())
    }

}

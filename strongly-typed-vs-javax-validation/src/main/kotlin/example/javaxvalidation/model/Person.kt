package example.javaxvalidation.model

import example.javaxvalidation.validation.Age
import example.javaxvalidation.validation.FirstName
import example.javaxvalidation.validation.LastName
import java.util.*
import javax.validation.Valid

data class Person(
    val id: UUID? = null,
    @field:FirstName
    val firstName: String,
    @field:LastName
    val lastName: String,
    @field:Age
    val age: Int,
    @field:Valid
    val address: Address
)

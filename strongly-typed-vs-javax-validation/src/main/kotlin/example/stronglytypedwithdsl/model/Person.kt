package example.stronglytypedwithdsl.model

import java.util.*

data class Person(
    val id: UUID? = null,
    val firstName: FirstName,
    val lastName: LastName,
    val age: Age,
    val address: Address
)


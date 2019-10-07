package example.model

import java.util.UUID

data class Person(
    val id: UUID? = null,
    val firstName: FirstName,
    val lastName: LastName,
    val age: Age,
    val address: Address
)


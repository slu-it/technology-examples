package example.stronglytyped.model

data class Address(
    val street: Street,
    val zipCode: ZipCode,
    val city: City
)

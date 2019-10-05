package example.stronglytypedwithdsl.model

data class Address(
    val street: Street,
    val zipCode: ZipCode,
    val city: City
)

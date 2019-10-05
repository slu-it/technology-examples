package example.javaxvalidation.model

import example.javaxvalidation.validation.City
import example.javaxvalidation.validation.Street
import example.javaxvalidation.validation.ZipCode

data class Address(
    @field:Street
    val street: String,
    @field:ZipCode
    val zipCode: String,
    @field:City
    val city: String
)

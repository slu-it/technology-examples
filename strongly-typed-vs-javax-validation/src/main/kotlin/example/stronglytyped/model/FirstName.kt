package example.stronglytyped.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import example.stronglytyped.validation.doesNotContainsAny
import example.stronglytyped.validation.possibleInjectionCharacters
import example.stronglytyped.validation.validate


data class FirstName @JsonCreator constructor(
    @JsonValue private val value: String
) {

    init {
        validate(value.isNotBlank()) {
            "Value of First Name must not be blank!"
        }
        validate(value.length <= 50) {
            "Value of First Name must not exceed 50 characters. [$value] is ${value.length} characters long!"
        }

        val illegalCharacters = possibleInjectionCharacters - '\''
        validate(value doesNotContainsAny illegalCharacters) {
            "Value of First Name must not contain any illegal characters. [$value] contains at least one of these: $illegalCharacters"
        }
    }

    override fun toString(): String = value

}

package example.stronglytyped.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import example.stronglytyped.validation.doesNotContainsAny
import example.stronglytyped.validation.possibleInjectionCharacters
import example.stronglytyped.validation.validate

data class Street @JsonCreator constructor(
    @JsonValue private val value: String
) {

    init {
        validate(value.isNotBlank()) {
            "Value of Street must not be blank!"
        }
        validate(value.length <= 100) {
            "Value of Street must not exceed 100 characters. [$value] is ${value.length} characters long!"
        }
        validate(value doesNotContainsAny possibleInjectionCharacters) {
            "Value of Street must not contain any illegal characters. [$value] contains at least one of these: $possibleInjectionCharacters"
        }
    }

    override fun toString(): String = value

}

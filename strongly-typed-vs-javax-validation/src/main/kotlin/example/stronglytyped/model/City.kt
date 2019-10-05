package example.stronglytyped.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import example.stronglytyped.validation.doesNotContainsAny
import example.stronglytyped.validation.possibleInjectionCharacters
import example.stronglytyped.validation.validate

data class City @JsonCreator constructor(
    @JsonValue private val value: String
) {

    init {
        validate(value.isNotBlank()) {
            "Value of City must not be blank!"
        }
        validate(value.length <= 50) {
            "Value of City must not exceed 50 characters. [$value] is ${value.length} characters long!"
        }
        validate(value doesNotContainsAny possibleInjectionCharacters) {
            "Value of City must not contain any illegal characters. [$value] contains at least one of these: $possibleInjectionCharacters"
        }
    }

    override fun toString(): String = value

}

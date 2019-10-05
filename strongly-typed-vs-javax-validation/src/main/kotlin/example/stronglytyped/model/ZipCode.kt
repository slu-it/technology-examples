package example.stronglytyped.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import example.stronglytyped.validation.doesNotContainsAny
import example.stronglytyped.validation.possibleInjectionCharacters
import example.stronglytyped.validation.validate

data class ZipCode @JsonCreator constructor(
    @JsonValue private val value: String
) {

    init {
        validate(value.isNotBlank()) {
            "Value of ZIP Code must not be blank!"
        }
        validate(value.length <= 15) {
            "Value of ZIP Code must not exceed 15 characters. [$value] is ${value.length} characters long!"
        }
        validate(value doesNotContainsAny possibleInjectionCharacters) {
            "Value of ZIP Code must not contain any illegal characters. [$value] contains at least one of these: $possibleInjectionCharacters"
        }
    }

    override fun toString(): String = value

}

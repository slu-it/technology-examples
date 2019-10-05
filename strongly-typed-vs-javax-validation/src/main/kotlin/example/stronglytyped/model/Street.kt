package example.stronglytyped.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import example.stronglytyped.validation.doesNotContainsAny
import example.stronglytyped.validation.possibleInjectionCharacters

data class Street @JsonCreator constructor(
    @JsonValue private val value: String
) {

    init {
        require(value.isNotBlank()) {
            "Value of Street must not be blank!"
        }
        require(value.length <= 100) {
            "Value of Street must not exceed 100 characters. [$value] is ${value.length} characters long!"
        }
        require(value doesNotContainsAny possibleInjectionCharacters) {
            "Value of Street must not contain any illegal characters. [$value] contains at least one of these: $possibleInjectionCharacters"
        }
    }

    override fun toString(): String = value

}

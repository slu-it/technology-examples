package example.model

import example.validation.Validation.Companion.validate
import example.validation.doesNotContainAny
import example.validation.hasMaxLengthOf
import example.validation.isNotBlank
import example.validation.possibleInjectionCharacters

data class City(private val value: String) {

    init {
        validate(value, "City") {
            isNotBlank()
            hasMaxLengthOf(50)
            doesNotContainAny(possibleInjectionCharacters)
        }
    }

    override fun toString(): String = value

}

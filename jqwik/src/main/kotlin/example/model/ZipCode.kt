package example.model

import example.validation.Validation.Companion.validate
import example.validation.doesNotContainAny
import example.validation.hasMaxLengthOf
import example.validation.isNotBlank
import example.validation.possibleInjectionCharacters

data class ZipCode(private val value: String) {

    init {
        validate(value, "ZIP Code") {
            isNotBlank()
            hasMaxLengthOf(15)
            doesNotContainAny(possibleInjectionCharacters)
        }
    }

    override fun toString(): String = value

}

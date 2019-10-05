package example.stronglytypedwithdsl.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import example.stronglytypedwithdsl.validation.Validation.Companion.validate
import example.stronglytypedwithdsl.validation.doesNotContainAny
import example.stronglytypedwithdsl.validation.hasMaxLengthOf
import example.stronglytypedwithdsl.validation.isNotBlank
import example.stronglytypedwithdsl.validation.possibleInjectionCharacters

data class City @JsonCreator constructor(
    @JsonValue private val value: String
) {

    init {
        validate(value, "City") {
            isNotBlank()
            hasMaxLengthOf(50)
            doesNotContainAny(possibleInjectionCharacters)
        }
    }

    override fun toString(): String = value

}

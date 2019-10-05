package example.stronglytypedwithdsl.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import example.stronglytypedwithdsl.validation.Validation.Companion.validate
import example.stronglytypedwithdsl.validation.isGreaterThanOrEqualTo
import example.stronglytypedwithdsl.validation.isLessThanOrEqualTo

data class Age @JsonCreator constructor(
    @JsonValue private val value: Int
) {

    init {
        validate(value, "Age") {
            isGreaterThanOrEqualTo(0)
            isLessThanOrEqualTo(150)
        }
    }

    override fun toString(): String = value.toString()

}

package example.model

import example.validation.Validation.Companion.validate
import example.validation.isGreaterThanOrEqualTo
import example.validation.isLessThanOrEqualTo

data class Age(private val value: Int) {

    init {
        validate(value, "Age") {
            isGreaterThanOrEqualTo(0)
            isLessThanOrEqualTo(150)
        }
    }

    override fun toString(): String = value.toString()

}

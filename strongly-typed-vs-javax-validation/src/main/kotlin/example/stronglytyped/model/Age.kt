package example.stronglytyped.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

data class Age @JsonCreator constructor(
    @JsonValue private val value: Int
) {

    init {
        require(value >= 0) {
            "Value of Age must be greater than or equal to 0, but was $value!"
        }
        require(value <= 150) {
            "Value of Age must be less than or equal to 150, but was $value!"
        }
    }

    override fun toString(): String = value.toString()

}

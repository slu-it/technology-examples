package library.service.domain.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

fun title(value: String): Title = Title(value)

data class Title @JsonCreator constructor(
    @JsonValue private val value: String
) {
    override fun toString(): String = value
}

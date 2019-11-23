package library.service.domain.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

fun isbn(value: String): Isbn = Isbn(value)

data class Isbn @JsonCreator constructor(
    @JsonValue private val value: String
) {
    override fun toString(): String = value
}

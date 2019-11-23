package library.service.domain.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

fun userName(value: String): UserName = UserName(value)

data class UserName @JsonCreator constructor(
    @JsonValue private val value: String
) {
    override fun toString(): String = value
}

package library.service.domain.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

fun borrower(value: String): Borrower = Borrower(value)

data class Borrower @JsonCreator constructor(
    @JsonValue private val value: String
) {
    override fun toString(): String = value
}

package example

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("MESSAGES")
data class Message(
    @Id
    var id: UUID? = null,
    var sender: String,
    var receiver: String,
    var content: String
)

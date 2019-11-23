package library.service.domain.events

import library.service.domain.model.BookRecord
import library.service.domain.model.UserName
import java.time.Instant
import java.util.*

data class BookRecordAdded(
    override val id: UUID,
    override val userName: UserName,
    override val timestamp: Instant,
    val bookRecord: BookRecord
) : BusinessFunctionEvent()

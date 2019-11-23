package library.service.domain.events

import library.service.domain.model.UserName
import java.time.Instant
import java.util.*

data class BookRecordRemoved(
    override val id: UUID,
    override val userName: UserName,
    override val timestamp: Instant,
    val bookRecordId: UUID
) : BusinessFunctionEvent()

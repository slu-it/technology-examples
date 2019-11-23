package library.service.domain.functions.books.remove

import library.service.domain.events.BookRecordRemoved
import library.service.domain.functions.UserContext
import library.service.domain.stereotypes.TechnicalFunction
import org.springframework.context.ApplicationEventPublisher
import org.springframework.util.IdGenerator
import java.time.Clock
import java.util.*

@TechnicalFunction
class PublishBookRecordDeletedEvent(
    private val eventPublisher: ApplicationEventPublisher,
    private val idGenerator: IdGenerator,
    private val userContext: UserContext,
    private val clock: Clock
) {

    operator fun invoke(bookRecordId: UUID) {
        val event = BookRecordRemoved(
            id = idGenerator.generateId(),
            userName = userContext.getCurrentUserName(),
            timestamp = clock.instant(),
            bookRecordId = bookRecordId
        )
        eventPublisher.publishEvent(event)
    }

}

package library.service.domain.functions.books.add

import library.service.domain.events.BookRecordAdded
import library.service.domain.functions.UserContext
import library.service.domain.model.BookRecord
import library.service.domain.stereotypes.TechnicalFunction
import org.springframework.context.ApplicationEventPublisher
import org.springframework.util.IdGenerator
import java.time.Clock

@TechnicalFunction
class PublishBookRecordAddedEvent(
    private val eventPublisher: ApplicationEventPublisher,
    private val idGenerator: IdGenerator,
    private val userContext: UserContext,
    private val clock: Clock
) {

    operator fun invoke(bookRecord: BookRecord) {
        val event = BookRecordAdded(
            id = idGenerator.generateId(),
            userName = userContext.getCurrentUserName(),
            timestamp = clock.instant(),
            bookRecord = bookRecord
        )
        eventPublisher.publishEvent(event)
    }

}

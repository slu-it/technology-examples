package library.service.domain.functions.books.add

import library.service.domain.functions.UserContext
import library.service.domain.model.Available
import library.service.domain.model.Book
import library.service.domain.model.BookRecord
import library.service.domain.model.CreationData
import library.service.domain.model.UpdateData
import library.service.domain.stereotypes.BusinessFunction
import library.service.security.CanOnlyBeExecutedByCurators
import org.springframework.util.IdGenerator
import java.time.Clock

@BusinessFunction
class AddBookToCollection(
    private val idGenerator: IdGenerator,
    private val clock: Clock,
    private val userContext: UserContext,
    private val insertBookRecordIntoDataStore: InsertBookRecordIntoDataStore,
    private val publishBookRecordAddedEvent: PublishBookRecordAddedEvent
) {

    @CanOnlyBeExecutedByCurators
    operator fun invoke(book: Book): BookRecord {
        val id = idGenerator.generateId()
        val now = clock.instant()
        val userName = userContext.getCurrentUserName()

        val bookRecord = BookRecord(
            id = id,
            book = book,
            state = Available(since = now),
            created = CreationData(at = now, by = userName),
            lastUpdated = UpdateData(at = now, by = userName)
        )

        insertBookRecordIntoDataStore(bookRecord)
        publishBookRecordAddedEvent(bookRecord)
        return bookRecord
    }

}

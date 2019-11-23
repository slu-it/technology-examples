package library.service.domain.functions.books.get

import library.service.domain.stereotypes.BusinessFunction
import library.service.domain.model.BookRecord
import library.service.security.CanBeExecutedByAnyUser
import java.util.*

@BusinessFunction
class GetBookRecordFromCollectionById(
    private val getBookRecordFromDataStoreById: GetBookRecordFromDataStoreById
) {

    @CanBeExecutedByAnyUser
    operator fun invoke(id: UUID): BookRecord? {
        return getBookRecordFromDataStoreById(id)
    }

}

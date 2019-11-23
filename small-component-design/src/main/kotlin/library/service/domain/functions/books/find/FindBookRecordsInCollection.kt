package library.service.domain.functions.books.find

import library.service.domain.stereotypes.BusinessFunction
import library.service.domain.model.BookRecordPage
import library.service.domain.model.FindBookRecordsQuery
import library.service.security.CanBeExecutedByAnyUser

@BusinessFunction
class FindBookRecordsInCollection(
    private val findBookRecordsInDataStore: FindBookRecordsInDataStore
) {

    @CanBeExecutedByAnyUser
    operator fun invoke(query: FindBookRecordsQuery): BookRecordPage {
        return findBookRecordsInDataStore(query)
    }

}

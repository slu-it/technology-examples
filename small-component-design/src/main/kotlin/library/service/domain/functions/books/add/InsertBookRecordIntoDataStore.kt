package library.service.domain.functions.books.add

import library.service.domain.functions.DataStoreException
import library.service.domain.functions.books.BookRecordRepository
import library.service.domain.functions.dataStoreOperation
import library.service.domain.model.BookRecord
import library.service.domain.stereotypes.TechnicalFunction

@TechnicalFunction
class InsertBookRecordIntoDataStore(
    private val repository: BookRecordRepository
) {

    @Throws(DataStoreException::class)
    operator fun invoke(bookRecord: BookRecord) {
        dataStoreOperation {
            repository.insert(bookRecord)
        }
    }

}

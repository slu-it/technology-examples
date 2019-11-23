package library.service.domain.functions.books.remove

import library.service.domain.functions.books.BookRecordRepository
import library.service.domain.stereotypes.TechnicalFunction
import java.util.*

@TechnicalFunction
class RemoveBookRecordFromDataStoreById(
    private val repository: BookRecordRepository
) {

    operator fun invoke(id: UUID): Boolean {
        if (repository.existsById(id)) {
            repository.deleteById(id)
            return true
        }
        return false
    }

}

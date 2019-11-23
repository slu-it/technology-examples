package library.service.domain.functions.books.get

import library.service.domain.stereotypes.TechnicalFunction
import library.service.domain.functions.books.BookRecordRepository
import library.service.domain.model.BookRecord
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@TechnicalFunction
class GetBookRecordFromDataStoreById(
    private val repository: BookRecordRepository
) {

    operator fun invoke(id: UUID): BookRecord? {
        return repository.findByIdOrNull(id)
    }

}

package library.service.domain.functions.books

import library.service.domain.model.BookRecord
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface BookRecordRepository : MongoRepository<BookRecord, UUID> {
    @Query("{ 'book.title.value' : { '\$regex' : ?0 , \$options: 'i'}}")
    fun findByTitle(titleRegEx: String, pageable: Pageable): Page<BookRecord>
}

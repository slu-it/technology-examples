package library.service.domain.functions.books.get

import library.service.domain.model.BookRecordResource
import library.service.domain.model.toResource
import library.service.domain.stereotypes.HttpAdapter
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@HttpAdapter
@RequestMapping("/api/books/{id}")
class GetBookRecordFromCollectionByIdHttpAdapter(
    private val getBookRecordFromCollectionById: GetBookRecordFromCollectionById
) {

    @GetMapping
    fun get(@PathVariable id: UUID): ResponseEntity<BookRecordResource> {
        val bookRecord = getBookRecordFromCollectionById(id)
        if (bookRecord != null) {
            return ok(bookRecord.toResource())
        }
        return notFound().build()
    }

}

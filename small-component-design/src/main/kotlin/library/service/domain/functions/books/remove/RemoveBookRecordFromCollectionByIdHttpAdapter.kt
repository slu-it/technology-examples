package library.service.domain.functions.books.remove

import library.service.domain.stereotypes.HttpAdapter
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.noContent
import org.springframework.http.ResponseEntity.notFound
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@HttpAdapter
@RequestMapping("/api/books/{id}")
class RemoveBookRecordFromCollectionByIdHttpAdapter(
    private val removeBookRecordFromCollectionById: RemoveBookRecordFromCollectionById
) {

    @DeleteMapping
    fun delete(@PathVariable id: UUID): ResponseEntity<Unit> {
        if (removeBookRecordFromCollectionById(id)) {
            return noContent().build()
        }
        return notFound().build()
    }

}

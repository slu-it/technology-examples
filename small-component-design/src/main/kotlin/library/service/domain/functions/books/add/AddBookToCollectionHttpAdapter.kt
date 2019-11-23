package library.service.domain.functions.books.add

import library.service.domain.model.Book
import library.service.domain.model.BookRecordResource
import library.service.domain.model.toResource
import library.service.domain.stereotypes.HttpAdapter
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@HttpAdapter
@RequestMapping("/api/books")
class AddBookToCollectionHttpAdapter(
    private val addBookToCollection: AddBookToCollection
) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun post(@RequestBody book: Book): BookRecordResource {
        return addBookToCollection(book).toResource()
    }

}

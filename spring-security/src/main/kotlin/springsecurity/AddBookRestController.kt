package springsecurity

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class AddBookRestController(
    private val usecase: AddBookUsecase
) {

    @ResponseStatus(CREATED)
    @PostMapping("/api/books")
    fun postBook(@RequestBody book: Book): BookRecord {
        return usecase(book)
    }

}

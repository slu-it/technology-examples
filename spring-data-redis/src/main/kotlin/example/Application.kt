package example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Component
import java.io.Serializable

@EnableCaching
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Component
class GetBookUsecase(
    val bookDataSource: SlowBookDataSource
) {

    fun get(isbn: String, salt: String): Book {
        return bookDataSource.getBook(isbn, NonSerializeableSalt(salt)) ?: throw BookNotFoundException(isbn)
    }

}

@Component
class SlowBookDataSource {

    private val books: Map<String, Book> = mapOf(
        "978-0132350884" to Book(isbn = "978-0132350884", title = "Clean Code"),
        "978-0137081073" to Book(isbn = "978-0137081073", title = "Clean Coder"),
        "978-0134494166" to Book(isbn = "978-0134494166", title = "Clean Architecture")
    )

    @Cacheable("book-cache", unless = "#result == null")
    fun getBook(isbn: String, salt: NonSerializeableSalt): Book? {
        Thread.sleep(1000)
        return books[isbn]
    }

}

data class NonSerializeableSalt(
    val salt: String
)

data class Book(
    val isbn: String,
    val title: String
) : Serializable

class BookNotFoundException(isbn: String) : RuntimeException("Could not find book for ISBN [$isbn]")

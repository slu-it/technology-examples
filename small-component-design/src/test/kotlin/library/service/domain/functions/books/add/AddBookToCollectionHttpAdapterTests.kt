package library.service.domain.functions.books.add

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import library.service.test.BookRecords
import library.service.test.Books
import library.service.test.TechnologyIntegrationTest
import library.service.test.strictJson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.hateoas.MediaTypes.HAL_JSON
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WithMockUser
@TechnologyIntegrationTest
@WebMvcTest(AddBookToCollectionHttpAdapter::class)
internal class AddBookToCollectionHttpAdapterTests(
    @Autowired val mockMvc: MockMvc
) {

    @MockkBean
    lateinit var addBookToCollection: AddBookToCollection

    @Test
    fun `posting a book will respond with the created record data`() {
        val book = Books.DANCE_WITH_DRAGONS
        val bookRecord = BookRecords.DANCE_WITH_DRAGONS

        every { addBookToCollection(book) } returns bookRecord

        mockMvc
            .post("/api/books") {
                contentType = APPLICATION_JSON
                content = """{ "isbn": "978-0006486114", "title": "A Dance With Dragons" }"""
            }
            .andExpect {
                status { isCreated }
                content {
                    contentType(HAL_JSON)
                    strictJson {
                        """
                        {
                          "isbn": "978-0006486114",
                          "title": "A Dance With Dragons",
                          "_links": {
                            "self": {
                              "href": "http://localhost/api/books/41d14c4a-0f3d-4750-84cb-ffd12b0f8ee4"
                            }
                          }
                        }
                        """
                    }
                }
            }
    }

}

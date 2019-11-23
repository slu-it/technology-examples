package library.service.domain.functions.books.get

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import library.service.test.BookRecords.GAME_OF_THRONES
import library.service.test.BookRecords.GAME_OF_THRONES_BORROWED
import library.service.test.TechnologyIntegrationTest
import library.service.test.strictJson
import library.service.test.uuid
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.hateoas.MediaTypes.HAL_JSON
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WithMockUser
@TechnologyIntegrationTest
@WebMvcTest(GetBookRecordFromCollectionByIdHttpAdapter::class)
internal class GetBookRecordFromCollectionByIdHttpAdapterTests(
    @Autowired val mockMvc: MockMvc
) {

    @MockkBean
    lateinit var getBookRecordFromCollectionById: GetBookRecordFromCollectionById

    val id = uuid("ee52d8e5-28c7-406b-8575-11f40475b207")

    @Test
    fun `returns empty NOT FOUND if there is no book with the given ID`() {
        every { getBookRecordFromCollectionById(id) } returns null

        mockMvc.get("/api/books/$id")
            .andExpect {
                status { isNotFound }
                content { string("") }
            }
    }

    @Test
    fun `returns book record if there is one with the given ID - available `() {
        every { getBookRecordFromCollectionById(id) } returns GAME_OF_THRONES

        mockMvc.get("/api/books/$id")
            .andExpect {
                status { isOk }
                content {
                    contentType(HAL_JSON)
                    strictJson {
                        """
                        {
                          "isbn": "978-0553573404",
                          "title": "A Game of Thrones",
                          "_links": {
                            "self": {
                              "href": "http://localhost/api/books/ee52d8e5-28c7-406b-8575-11f40475b207"
                            }
                          }
                        }
                        """
                    }
                }
            }
    }

    @Test
    fun `returns book record if there is one with the given ID - borrowed `() {
        every { getBookRecordFromCollectionById(id) } returns GAME_OF_THRONES_BORROWED

        mockMvc.get("/api/books/$id")
            .andExpect {
                status { isOk }
                content {
                    contentType(HAL_JSON)
                    strictJson {
                        """
                        {
                          "isbn": "978-0553573404",
                          "title": "A Game of Thrones",
                          "borrowed": {
                            "by": "slu",
                            "on": "2020-01-21T12:34:56.789Z"
                          },
                          "_links": {
                            "self": {
                              "href": "http://localhost/api/books/ee52d8e5-28c7-406b-8575-11f40475b207"
                            }
                          }
                        }
                        """
                    }
                }
            }
    }

}

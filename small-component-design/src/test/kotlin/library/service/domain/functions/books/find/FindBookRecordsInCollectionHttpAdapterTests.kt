package library.service.domain.functions.books.find

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import library.service.domain.model.BookRecordPage
import library.service.domain.model.FindBookRecordsQuery
import library.service.test.BookRecords.CLASH_OF_KINGS
import library.service.test.BookRecords.DANCE_WITH_DRAGONS_BORROWED
import library.service.test.BookRecords.FEAST_FOR_CROWS
import library.service.test.TechnologyIntegrationTest
import library.service.test.relaxedJson
import library.service.test.strictJson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.hateoas.MediaTypes.HAL_JSON
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WithMockUser
@TechnologyIntegrationTest
@WebMvcTest(FindBookRecordsInCollectionHttpAdapter::class)
internal class FindBookRecordsInCollectionHttpAdapterTests(
    @Autowired val mockMvc: MockMvc
) {

    @MockkBean
    lateinit var findBookRecordsInCollection: FindBookRecordsInCollection

    @Test
    fun `returns an empty page if there are no book records`() {
        every { findBookRecordsInCollection(any()) } returns BookRecordPage()

        mockMvc.get("/api/books")
            .andExpect {
                status { isOk }
                content {
                    contentType(HAL_JSON)
                    strictJson {
                        """
                        {
                          "_links": {
                            "self": { "href": "http://localhost/api/books?page=0&size=100" }
                          },
                          "page": {
                            "size": 100,
                            "totalElements": 0,
                            "totalPages": 1,
                            "number": 0
                          }
                        }
                        """
                    }
                }
            }

        verify { findBookRecordsInCollection(FindBookRecordsQuery()) }
    }

    @Test
    fun `returns a page with content if there are book records`() {
        every { findBookRecordsInCollection(any()) } returns BookRecordPage(
            content = listOf(CLASH_OF_KINGS, DANCE_WITH_DRAGONS_BORROWED),
            totalElements = 2
        )

        mockMvc.get("/api/books")
            .andExpect {
                status { isOk }
                content {
                    contentType(HAL_JSON)
                    strictJson {
                        """
                        {
                          "_embedded": {
                            "books": [
                              {
                                "isbn": "978-0553579901",
                                "title": "A Clash of Kings",
                                "_links": {
                                  "self": { "href": "http://localhost/api/books/523d4c4b-e934-4616-b1fd-d4dc82eddd8c" }
                                }
                              },
                              {
                                "isbn": "978-0006486114",
                                "title": "A Dance With Dragons",
                                "borrowed": {
                                  "by": "slu",
                                  "on": "2020-01-23T12:34:56.789Z"
                                },
                                "_links": {
                                  "self": { "href": "http://localhost/api/books/41d14c4a-0f3d-4750-84cb-ffd12b0f8ee4" }
                                }
                              }
                            ]
                          },
                          "_links": {
                            "self": { "href": "http://localhost/api/books?page=0&size=100" }
                          },
                          "page": {
                            "size": 100,
                            "totalElements": 2,
                            "totalPages": 1,
                            "number": 0
                          }
                        }
                        """
                    }
                }
            }

        verify { findBookRecordsInCollection(FindBookRecordsQuery()) }
    }

    @Test
    fun `returns a page with next page link for first of many pages`() {
        every { findBookRecordsInCollection(any()) } returns BookRecordPage(
            content = listOf(CLASH_OF_KINGS),
            totalElements = 3,
            pageNumber = 0,
            pageSize = 1,
            totalPages = 3,
            firstPage = true,
            lastPage = false
        )

        mockMvc.get("/api/books?page=0&size=1")
            .andExpect {
                status { isOk }
                content {
                    contentType(HAL_JSON)
                    relaxedJson {
                        """
                        {
                          "_links": {
                            "self": { "href": "http://localhost/api/books?page=0&size=1" },
                            "next-page": { "href": "http://localhost/api/books?page=1&size=1" }
                          }
                        }
                        """
                    }
                }
            }
    }

    @Test
    fun `returns a page with previous page link for last of many pages`() {
        every { findBookRecordsInCollection(any()) } returns BookRecordPage(
            content = listOf(FEAST_FOR_CROWS),
            totalElements = 3,
            pageNumber = 2,
            pageSize = 1,
            totalPages = 3,
            firstPage = false,
            lastPage = true
        )

        mockMvc.get("/api/books?page=2&size=1")
            .andExpect {
                status { isOk }
                content {
                    contentType(HAL_JSON)
                    relaxedJson {
                        """
                        {
                          "_links": {
                            "self": { "href": "http://localhost/api/books?page=2&size=1" },
                            "previous-page": { "href": "http://localhost/api/books?page=1&size=1" }
                          }
                        }
                        """
                    }
                }
            }
    }

    @Test
    fun `returns a page with previous and next page link for middle of many pages`() {
        every { findBookRecordsInCollection(any()) } returns BookRecordPage(
            content = listOf(FEAST_FOR_CROWS),
            totalElements = 3,
            pageNumber = 1,
            pageSize = 1,
            totalPages = 3,
            firstPage = false,
            lastPage = false
        )

        mockMvc.get("/api/books?page=1&size=1")
            .andExpect {
                status { isOk }
                content {
                    contentType(HAL_JSON)
                    relaxedJson {
                        """
                        {
                          "_links": {
                            "self": { "href": "http://localhost/api/books?page=1&size=1" },
                            "previous-page": { "href": "http://localhost/api/books?page=0&size=1" },
                            "next-page": { "href": "http://localhost/api/books?page=2&size=1" }
                          }
                        }
                        """
                    }
                }
            }
    }

    @Test
    fun `partial title can be provided as a request parameter`() {
        every { findBookRecordsInCollection(any()) } returns BookRecordPage()

        mockMvc.get("/api/books?title=of")
            .andExpect { status { isOk } }

        verify { findBookRecordsInCollection(FindBookRecordsQuery(title = "of")) }
    }

    @Test
    fun `page number and size can be configured as a request parameter`() {
        every { findBookRecordsInCollection(any()) } returns BookRecordPage()

        mockMvc.get("/api/books?page=15&size=42")
            .andExpect { status { isOk } }

        verify { findBookRecordsInCollection(FindBookRecordsQuery(pageNumber = 15, pageSize = 42)) }
    }

}

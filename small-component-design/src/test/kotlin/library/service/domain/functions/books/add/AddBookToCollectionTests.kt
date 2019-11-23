package library.service.domain.functions.books.add

import com.ninjasquad.springmockk.MockkBean
import io.mockk.mockk
import io.mockk.verify
import library.service.ApplicationConfiguration
import library.service.domain.model.Available
import library.service.domain.model.Book
import library.service.domain.model.BookRecord
import library.service.domain.model.CreationData
import library.service.domain.model.UpdateData
import library.service.domain.model.isbn
import library.service.domain.model.title
import library.service.domain.model.userName
import library.service.security.CURATOR
import library.service.security.MethodSecurityConfiguration
import library.service.security.USER
import library.service.test.Books
import library.service.test.TechnologyIntegrationTest
import library.service.test.UnitTest
import library.service.test.fixedUserContext
import library.service.test.instant
import library.service.test.sequentialClock
import library.service.test.sequentialIdGenerator
import library.service.test.uuid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.test.context.support.WithMockUser

internal class AddBookToCollectionTests {

    @Nested
    @UnitTest
    inner class FunctionalTests {

        val insertBookRecordIntoDataStore: InsertBookRecordIntoDataStore = mockk(relaxed = true)
        val publishBookRecordAddedEvent: PublishBookRecordAddedEvent = mockk(relaxed = true)

        val addBookToCollection = AddBookToCollection(
            idGenerator = sequentialIdGenerator("fe6cbb55-2c4e-4e7f-9b4e-11af44a21222"),
            clock = sequentialClock("2019-11-23T12:34:56.789Z"),
            userContext = fixedUserContext("slu"),
            insertBookRecordIntoDataStore = insertBookRecordIntoDataStore,
            publishBookRecordAddedEvent = publishBookRecordAddedEvent
        )

        @Test
        fun `adding a book to the collection returns the created record`() {
            val book = Book(
                isbn = isbn("978-1524796280"),
                title = title("Fire and Blood")
            )

            val actualBookRecord = addBookToCollection(book)

            val expectedBookRecord = BookRecord(
                id = uuid("fe6cbb55-2c4e-4e7f-9b4e-11af44a21222"),
                book = Book(
                    isbn = isbn("978-1524796280"),
                    title = title("Fire and Blood")
                ),
                state = Available(since = instant("2019-11-23T12:34:56.789Z")),
                created = CreationData(
                    at = instant("2019-11-23T12:34:56.789Z"),
                    by = userName("slu")
                ),
                lastUpdated = UpdateData(
                    at = instant("2019-11-23T12:34:56.789Z"),
                    by = userName("slu")
                )
            )
            assertThat(actualBookRecord).isEqualTo(expectedBookRecord)
        }

        @Test
        fun `adding a book to the collection persists the record in the data store`() {
            val bookRecord = addBookToCollection(Books.GAME_OF_THRONES)
            verify { insertBookRecordIntoDataStore(bookRecord) }
        }

        @Test
        fun `adding a book to the collection publishes an event`() {
            val bookRecord = addBookToCollection(Books.STORM_OF_SWORDS)
            verify { publishBookRecordAddedEvent(bookRecord) }
        }

    }

    @Nested
    @TechnologyIntegrationTest
    @SpringBootTest(classes = [SecurityTestsConfiguration::class])
    inner class SecurityTests(
        @Autowired val addBookToCollection: AddBookToCollection
    ) {

        @MockkBean(relaxed = true)
        lateinit var insertBookRecordIntoDataStore: InsertBookRecordIntoDataStore

        @MockkBean(relaxed = true)
        lateinit var publishBookRecordAddedEvent: PublishBookRecordAddedEvent

        @Test
        fun `function cannot be invoked without an authenticated user`() {
            assertThrows<AuthenticationCredentialsNotFoundException> {
                addBookToCollection(Books.FEAST_FOR_CROWS)
            }
        }

        @Test
        @WithMockUser(roles = [USER])
        fun `function cannot be invoked by a user not having the 'CURATOR' role`() {
            assertThrows<AccessDeniedException> {
                addBookToCollection(Books.FEAST_FOR_CROWS)
            }
        }

        @Test
        @WithMockUser(roles = [USER, CURATOR])
        fun `function can be invoked by any user having the 'CURATOR' role`() {
            addBookToCollection(Books.FEAST_FOR_CROWS)
        }

    }

    @Import(
        AddBookToCollection::class,
        ApplicationConfiguration::class,
        MethodSecurityConfiguration::class
    )
    private class SecurityTestsConfiguration

}

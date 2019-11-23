package library.service.domain.functions.books.remove

import com.ninjasquad.springmockk.MockkBean
import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import library.service.security.CURATOR
import library.service.security.MethodSecurityConfiguration
import library.service.security.USER
import library.service.test.TechnologyIntegrationTest
import library.service.test.UnitTest
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

internal class RemoveBookRecordFromCollectionByIdTests {

    val id = uuid()

    @Nested
    @UnitTest
    inner class FunctionalTests {

        val removeBookRecordFromDataStoreById: RemoveBookRecordFromDataStoreById = mockk()
        val publishBookRecordDeletedEvent: PublishBookRecordDeletedEvent = mockk(relaxed = true)
        val removeBookRecordFromCollectionById =
            RemoveBookRecordFromCollectionById(removeBookRecordFromDataStoreById, publishBookRecordDeletedEvent)

        @Test
        fun `function returns true if book record was successfully removed`() {
            every { removeBookRecordFromDataStoreById(id) } returns true
            assertThat(removeBookRecordFromCollectionById(id)).isTrue()
        }

        @Test
        fun `function returns false if book record was not removed`() {
            every { removeBookRecordFromDataStoreById(id) } returns false
            assertThat(removeBookRecordFromCollectionById(id)).isFalse()
        }

        @Test
        fun `successfully removing a book record publishes an event`() {
            every { removeBookRecordFromDataStoreById(id) } returns true
            removeBookRecordFromCollectionById(id)
            verify { publishBookRecordDeletedEvent(id) }
        }

        @Test
        fun `not removing a book record does NOT publish any events`() {
            every { removeBookRecordFromDataStoreById(id) } returns false
            removeBookRecordFromCollectionById(id)
            verify { publishBookRecordDeletedEvent wasNot called }
        }

    }

    @Nested
    @TechnologyIntegrationTest
    @SpringBootTest(classes = [SecurityTestsConfiguration::class])
    inner class SecurityTests(
        @Autowired val removeBookRecordFromCollectionById: RemoveBookRecordFromCollectionById
    ) {

        @MockkBean(relaxed = true)
        lateinit var removeBookRecordFromDataStoreById: RemoveBookRecordFromDataStoreById

        @MockkBean(relaxed = true)
        lateinit var publishBookRecordDeletedEvent: PublishBookRecordDeletedEvent

        @Test
        fun `function cannot be invoked without an authenticated user`() {
            assertThrows<AuthenticationCredentialsNotFoundException> {
                removeBookRecordFromCollectionById(id)
            }
        }

        @Test
        @WithMockUser(roles = [USER])
        fun `function cannot be invoked by a user not having the 'CURATOR' role`() {
            assertThrows<AccessDeniedException> {
                removeBookRecordFromCollectionById(id)
            }
        }

        @Test
        @WithMockUser(roles = [USER, CURATOR])
        fun `function can be invoked by any user having the 'CURATOR' role`() {
            removeBookRecordFromCollectionById(id)
        }

    }

    @Import(
        RemoveBookRecordFromCollectionById::class,
        MethodSecurityConfiguration::class
    )
    private class SecurityTestsConfiguration

}

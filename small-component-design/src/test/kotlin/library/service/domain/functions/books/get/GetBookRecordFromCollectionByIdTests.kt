package library.service.domain.functions.books.get

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import io.mockk.mockk
import library.service.security.MethodSecurityConfiguration
import library.service.security.USER
import library.service.test.BookRecords.CLASH_OF_KINGS
import library.service.test.BookRecords.GAME_OF_THRONES
import library.service.test.TechnologyIntegrationTest
import library.service.test.UnitTest
import library.service.test.uuid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.test.context.support.WithMockUser

internal class GetBookRecordFromCollectionByIdTests {

    val id = uuid()

    @Nested
    @UnitTest
    inner class FunctionalTests {

        val getBookRecordFromDataStoreById: GetBookRecordFromDataStoreById = mockk()
        val getBookRecordFromCollectionById = GetBookRecordFromCollectionById(getBookRecordFromDataStoreById)

        @TestFactory
        fun `function returns whatever book record the data store returned`() =
            listOf(GAME_OF_THRONES, CLASH_OF_KINGS, null).map { bookRecord ->
                dynamicTest("${bookRecord?.book?.title}") {
                    every { getBookRecordFromDataStoreById(id) } returns bookRecord
                    assertThat(getBookRecordFromCollectionById(id)).isSameAs(bookRecord)
                }
            }

    }

    @Nested
    @TechnologyIntegrationTest
    @SpringBootTest(classes = [MethodSecurityConfiguration::class])
    inner class SecurityTests {

        @MockkBean(relaxed = true)
        lateinit var getBookRecordFromDataStoreById: GetBookRecordFromDataStoreById
        @SpykBean
        lateinit var getBookRecordFromCollectionById: GetBookRecordFromCollectionById

        @Test
        fun `function cannot be invoked without an authenticated user`() {
            assertThrows<AuthenticationCredentialsNotFoundException> {
                getBookRecordFromCollectionById(id)
            }
        }

        @Test
        @WithMockUser(roles = [USER])
        fun `function can be invoked by any user having the 'USER' role`() {
            getBookRecordFromCollectionById(id)
        }

    }

}

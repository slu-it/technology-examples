package library.service.domain.functions.books.find

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import io.mockk.mockk
import library.service.domain.model.BookRecordPage
import library.service.domain.model.FindBookRecordsQuery
import library.service.security.MethodSecurityConfiguration
import library.service.security.USER
import library.service.test.TechnologyIntegrationTest
import library.service.test.UnitTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.test.context.support.WithMockUser

internal class FindBookRecordsInCollectionTests {

    @Nested
    @UnitTest
    inner class FunctionalTests {

        val findBookRecordsInDataStore: FindBookRecordsInDataStore = mockk()
        val findBookRecordsInCollection = FindBookRecordsInCollection(findBookRecordsInDataStore)

        @Test
        fun `function returns whatever the data store returned`() {
            val query = FindBookRecordsQuery()
            val page = BookRecordPage()
            every { findBookRecordsInDataStore(query) } returns page

            val resultPage = findBookRecordsInCollection(query)

            assertThat(resultPage).isSameAs(page)
        }

    }

    @Nested
    @TechnologyIntegrationTest
    @SpringBootTest(classes = [MethodSecurityConfiguration::class])
    inner class SecurityTests {

        @MockkBean(relaxed = true)
        lateinit var findBookRecordsInDataStore: FindBookRecordsInDataStore
        @SpykBean
        lateinit var findBookRecordsInCollection: FindBookRecordsInCollection

        @Test
        fun `function cannot be invoked without an authenticated user`() {
            assertThrows<AuthenticationCredentialsNotFoundException> {
                findBookRecordsInCollection(FindBookRecordsQuery())
            }
        }

        @Test
        @WithMockUser(roles = [USER])
        fun `function can be invoked by any user having the 'USER' role`() {
            findBookRecordsInCollection(FindBookRecordsQuery())
        }

    }

}

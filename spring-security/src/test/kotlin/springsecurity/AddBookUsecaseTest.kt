package springsecurity

import io.mockk.Called
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.test.context.support.WithMockUser
import java.util.*

internal class AddBookUsecaseTest {

    @Nested
    @UnitTest
    inner class FunctionalTests {

        val repository: BookRepository = mockk()
        val idGenerator: IdGenerator = mockk()

        val cut = AddBookUsecase(repository, idGenerator)

        @Test
        fun `invocation creates a BookRecord and persists it in the repository`() {
            every { idGenerator.generate() } returns uuid
            every { repository.create(any()) } answers { firstArg() }

            assertThat(cut.invoke(book)).isEqualTo(BookRecord(uuid, book))
            verify { repository.create(BookRecord(uuid, book)) }
        }

    }

    @Nested
    @IntegrationTest
    @SpringBootTest(classes = [AddBookUsecaseSecurityTestsConfiguration::class])
    inner class SecurityTests(
        @Autowired val repository: BookRepository,
        @Autowired val cut: AddBookUsecase
    ) {

        @AfterEach
        fun resetMocks() = clearAllMocks(answers = false)

        @Test
        fun `usecase cannot be invoked without authenticated user`() {
            assertThrows<AuthenticationCredentialsNotFoundException> { cut.invoke(book) }
        }

        @Test
        @WithMockUser(authorities = [ROLE_USER])
        fun `usecase cannot be invoked by user with just role USER`() {
            assertThrows<AccessDeniedException> { cut.invoke(book) }
            verify { repository wasNot Called }
        }

        @Test
        @WithMockUser(authorities = [ROLE_USER, ROLE_CURATOR])
        fun `usecase can be invoked by user with roles USER and CURATOR`() {
            cut.invoke(book)
        }

        @Test
        @WithMockUser(authorities = [ROLE_CURATOR])
        fun `usecase can be invoked by user with role CURATOR`() {
            cut.invoke(book)
        }

        @Test
        @WithMockUser(authorities = [ROLE_ADMIN])
        fun `usecase cannot be invoked by user with just role ADMIN`() {
            assertThrows<AccessDeniedException> { cut.invoke(book) }
            verify { repository wasNot Called }
        }

    }

    @Import(MethodSecurityConfiguration::class)
    class AddBookUsecaseSecurityTestsConfiguration {

        @Bean
        fun bookRepository(): BookRepository =
            mockk { every { create(any()) } answers { firstArg() } }

        @Bean
        fun idGenerator(): IdGenerator =
            mockk { every { generate() } answers { UUID.randomUUID() } }

        @Bean
        fun addBookUsecase(repository: BookRepository, idGenerator: IdGenerator) =
            AddBookUsecase(repository, idGenerator)

    }

}

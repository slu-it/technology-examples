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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.test.context.support.WithMockUser

internal class DeleteBookUsecaseTest {

    @Nested
    @UnitTest
    inner class FunctionalTests {

        val repository: BookRepository = mockk()

        val cut = DeleteBookUsecase(repository)

        @ParameterizedTest
        @ValueSource(booleans = [true, false])
        fun `invocation simply delegates to the repository`(mockedResult: Boolean) {
            every { repository.delete(uuid) } returns mockedResult
            assertThat(cut.invoke(uuid)).isEqualTo(mockedResult)
        }

    }

    @Nested
    @IntegrationTest
    @SpringBootTest(classes = [DeleteBookUsecaseTestConfiguration::class])
    inner class SecurityTests(
        @Autowired val repository: BookRepository,
        @Autowired val cut: DeleteBookUsecase
    ) {

        @AfterEach
        fun resetMocks() = clearAllMocks(answers = false)

        @Test
        fun `usecase cannot be invoked without authenticated user`() {
            assertThrows<AuthenticationCredentialsNotFoundException> { cut.invoke(uuid) }
        }

        @Test
        @WithMockUser(authorities = [ROLE_USER])
        fun `usecase cannot be invoked by user with just role USER`() {
            assertThrows<AccessDeniedException> { cut.invoke(uuid) }
            verify { repository wasNot Called }
        }

        @Test
        @WithMockUser(authorities = [ROLE_USER, ROLE_CURATOR])
        fun `usecase can be invoked by user with roles USER and CURATOR`() {
            cut.invoke(uuid)
        }

        @Test
        @WithMockUser(authorities = [ROLE_CURATOR])
        fun `usecase can be invoked by user with role CURATOR`() {
            cut.invoke(uuid)
        }

        @Test
        @WithMockUser(authorities = [ROLE_ADMIN])
        fun `usecase cannot be invoked by user with just role ADMIN`() {
            assertThrows<AccessDeniedException> { cut.invoke(uuid) }
            verify { repository wasNot Called }
        }

    }

    @Import(MethodSecurityConfiguration::class)
    class DeleteBookUsecaseTestConfiguration {

        @Bean
        fun bookRepository(): BookRepository = mockk { every { delete(any()) } returns true }

        @Bean
        fun deleteBookUsecase(repository: BookRepository) = DeleteBookUsecase(repository)

    }

}

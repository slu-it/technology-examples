package library.service.domain.functions.books.remove

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import library.service.test.TechnologyIntegrationTest
import library.service.test.uuid
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete

@WithMockUser
@TechnologyIntegrationTest
@WebMvcTest(RemoveBookRecordFromCollectionByIdHttpAdapter::class)
internal class RemoveBookRecordFromCollectionByIdHttpAdapterTests(
    @Autowired val mockMvc: MockMvc
) {

    @MockkBean
    lateinit var removeBookRecordFromCollectionById: RemoveBookRecordFromCollectionById

    val id = uuid("ee52d8e5-28c7-406b-8575-11f40475b207")

    @Test
    fun `returns empty NOT FOUND if there is no book with the given ID`() {
        every { removeBookRecordFromCollectionById(id) } returns false

        mockMvc.delete("/api/books/$id")
            .andExpect {
                status { isNotFound }
                content { string("") }
            }
    }

    @Test
    fun `returns empty NO CONTENT if there is no book with the given ID`() {
        every { removeBookRecordFromCollectionById(id) } returns true

        mockMvc.delete("/api/books/$id")
            .andExpect {
                status { isNoContent }
                content { string("") }
            }
    }

}

package library.service.domain.functions.books.remove

import com.ninjasquad.springmockk.SpykBean
import library.service.domain.functions.books.BookRecordRepository
import library.service.domain.functions.books.add.InsertBookRecordIntoDataStore
import library.service.test.BookRecords.CLASH_OF_KINGS
import library.service.test.BookRecords.GAME_OF_THRONES
import library.service.test.RunWithDockerizedMongoDb
import library.service.test.TechnologyIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@TechnologyIntegrationTest
@RunWithDockerizedMongoDb
@DataMongoTest(properties = ["spring.data.mongodb.port=\${MONGODB_PORT}"])
internal class RemoveBookRecordFromDataStoreByIdTests(
    @Autowired val repository: BookRecordRepository
) {

    @SpykBean
    lateinit var cut: RemoveBookRecordFromDataStoreById

    @Test
    fun `deleting existing book record returns true`() {
        InsertBookRecordIntoDataStore(repository).invoke(GAME_OF_THRONES)
        assertThat(cut(GAME_OF_THRONES.id)).isTrue()
    }

    @Test
    fun `deleting non-existing book record returns false`() {
        assertThat(cut(CLASH_OF_KINGS.id)).isFalse()
    }

}

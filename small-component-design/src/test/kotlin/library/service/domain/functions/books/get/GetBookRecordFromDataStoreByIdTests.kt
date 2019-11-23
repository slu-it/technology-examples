package library.service.domain.functions.books.get

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
internal class GetBookRecordFromDataStoreByIdTests(
    @Autowired val repository: BookRecordRepository
) {

    @SpykBean
    lateinit var cut: GetBookRecordFromDataStoreById

    @Test
    fun `existing book record can be found by its ID`() {
        InsertBookRecordIntoDataStore(repository).invoke(GAME_OF_THRONES)
        assertThat(cut(GAME_OF_THRONES.id)).isEqualTo(GAME_OF_THRONES)
    }

    @Test
    fun `non-existing book record returns null`() {
        assertThat(cut(CLASH_OF_KINGS.id)).isNull()
    }

}

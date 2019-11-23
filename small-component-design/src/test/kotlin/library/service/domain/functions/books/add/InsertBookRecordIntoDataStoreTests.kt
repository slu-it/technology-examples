package library.service.domain.functions.books.add

import com.ninjasquad.springmockk.SpykBean
import library.service.domain.functions.DataEntryAlreadyExistsException
import library.service.domain.functions.books.BookRecordRepository
import library.service.test.BookRecords.GAME_OF_THRONES
import library.service.test.BookRecords.GAME_OF_THRONES_BORROWED
import library.service.test.RunWithDockerizedMongoDb
import library.service.test.TechnologyIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@TechnologyIntegrationTest
@RunWithDockerizedMongoDb
@DataMongoTest(properties = ["spring.data.mongodb.port=\${MONGODB_PORT}"])
internal class InsertBookRecordIntoDataStoreTests(
    @Autowired val repository: BookRecordRepository
) {

    @SpykBean
    lateinit var insertBookRecordIntoDataStore: InsertBookRecordIntoDataStore

    @AfterEach
    fun cleanDatabase() = repository.deleteAll()

    @Test
    fun `a single BookRecord can be inserted and than found by its ID - available`() {
        val bookRecord = GAME_OF_THRONES
        insertBookRecordIntoDataStore(bookRecord)
        assertThat(repository.findById(bookRecord.id)).hasValue(bookRecord)
    }

    @Test
    fun `a single BookRecord can be inserted and than found by its ID - borrowed`() {
        val bookRecord = GAME_OF_THRONES_BORROWED
        insertBookRecordIntoDataStore(bookRecord)
        assertThat(repository.findById(bookRecord.id)).hasValue(bookRecord)
    }

    @Test
    fun `a single BookRecord cannot be inserted multiple times in a row`() {
        val bookRecord = GAME_OF_THRONES
        insertBookRecordIntoDataStore(bookRecord)
        assertThrows<DataEntryAlreadyExistsException> {
            insertBookRecordIntoDataStore(bookRecord)
        }
    }

}

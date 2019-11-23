package library.service.domain.functions.books.find

import com.ninjasquad.springmockk.SpykBean
import library.service.domain.functions.books.BookRecordRepository
import library.service.domain.functions.books.add.InsertBookRecordIntoDataStore
import library.service.domain.model.BookRecordPage
import library.service.domain.model.FindBookRecordsQuery
import library.service.test.BookRecords.CLASH_OF_KINGS
import library.service.test.BookRecords.DANCE_WITH_DRAGONS
import library.service.test.BookRecords.FEAST_FOR_CROWS
import library.service.test.BookRecords.GAME_OF_THRONES
import library.service.test.BookRecords.STORM_OF_SWORDS
import library.service.test.RunWithDockerizedMongoDb
import library.service.test.TechnologyIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@TechnologyIntegrationTest
@RunWithDockerizedMongoDb
@DataMongoTest(properties = ["spring.data.mongodb.port=\${MONGODB_PORT}"])
internal class FindBookRecordsInDataStoreTests(
    @Autowired val repository: BookRecordRepository
) {

    @SpykBean
    lateinit var cut: FindBookRecordsInDataStore

    @BeforeEach
    fun insertTestData() {
        val insertBookRecordIntoDataStore = InsertBookRecordIntoDataStore(repository)
        listOf(GAME_OF_THRONES, CLASH_OF_KINGS, STORM_OF_SWORDS, FEAST_FOR_CROWS, DANCE_WITH_DRAGONS)
            .forEach { insertBookRecordIntoDataStore(it) }
    }

    @AfterEach
    fun deleteDataStore() {
        repository.deleteAll()
    }

    @Test
    fun `simple query finds correct results`() {
        assertThat(cut(FindBookRecordsQuery(pageNumber = 0, pageSize = 10))).isEqualTo(
            BookRecordPage(
                content = listOf(CLASH_OF_KINGS, DANCE_WITH_DRAGONS, FEAST_FOR_CROWS, GAME_OF_THRONES, STORM_OF_SWORDS),
                pageNumber = 0, pageSize = 10,
                totalPages = 1, totalElements = 5,
                firstPage = true, lastPage = true
            )
        )
    }

    @Test
    fun `paging works as intended`() {
        assertThat(cut(FindBookRecordsQuery(pageNumber = 0, pageSize = 2))).isEqualTo(
            BookRecordPage(
                content = listOf(CLASH_OF_KINGS, DANCE_WITH_DRAGONS),
                pageNumber = 0, pageSize = 2,
                totalPages = 3, totalElements = 5,
                firstPage = true, lastPage = false
            )
        )
        assertThat(cut(FindBookRecordsQuery(pageNumber = 1, pageSize = 2))).isEqualTo(
            BookRecordPage(
                content = listOf(FEAST_FOR_CROWS, GAME_OF_THRONES),
                pageNumber = 1, pageSize = 2,
                totalPages = 3, totalElements = 5,
                firstPage = false, lastPage = false
            )
        )
        assertThat(cut(FindBookRecordsQuery(pageNumber = 2, pageSize = 2))).isEqualTo(
            BookRecordPage(
                content = listOf(STORM_OF_SWORDS),
                pageNumber = 2, pageSize = 2,
                totalPages = 3, totalElements = 5,
                firstPage = false, lastPage = true
            )
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["of", "OF"])
    fun `query with title finds correct results and is case insensitive`(title: String) {
        assertThat(cut(FindBookRecordsQuery(title = title, pageNumber = 0, pageSize = 10))).isEqualTo(
            BookRecordPage(
                content = listOf(CLASH_OF_KINGS, GAME_OF_THRONES, STORM_OF_SWORDS),
                pageNumber = 0, pageSize = 10,
                totalPages = 1, totalElements = 3,
                firstPage = true, lastPage = true
            )
        )
    }

}

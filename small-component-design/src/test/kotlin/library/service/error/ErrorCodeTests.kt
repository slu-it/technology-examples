package library.service.error

import library.service.error.ErrorCode.Category
import library.service.error.ErrorCode.Number
import library.service.test.UnitTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@UnitTest
internal class ErrorCodeTests {

    @Test
    fun `default error code is 'unknown'`() {
        assertThat(ErrorCode()).hasToString("UK-0000")
    }

    @CsvSource(
        "AA,    0, AA-0000",
        "BB,    1, BB-0001",
        "CC, 1000, CC-1000",
        "DD, 9999, DD-9999"
    )
    @ParameterizedTest
    fun `toString is generated correctly`(category: String, number: Int, expectedToString: String) {
        assertThat(ErrorCode(Category(category), Number(number))).hasToString(expectedToString)
    }

}

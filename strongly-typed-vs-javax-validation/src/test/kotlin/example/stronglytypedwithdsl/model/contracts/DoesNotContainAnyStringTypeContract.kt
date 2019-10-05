package example.stronglytypedwithdsl.model.contracts

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal interface DoesNotContainAnyStringTypeContract : StringTypeContract {

    val illegalCharacters: Iterable<Char>

    @TestFactory
    fun `instances containing an illegal character in their value cannot be initialized`(): List<DynamicTest> =
        illegalCharacters.map { illegalChar ->
            dynamicTest("$illegalChar") {
                assertThatThrownBy { createInstanceContaining(illegalChar) }
                    .hasMessageContaining("contains at least one of the following illegal characters:")
            }
        }

    fun createInstanceContaining(char: Char): Any

}

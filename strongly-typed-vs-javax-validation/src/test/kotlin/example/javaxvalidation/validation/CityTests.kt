package example.javaxvalidation.validation

import example.createStringOfLength
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class CityTests : ValidationTest() {

    val maxLength = 50
    val illegalCharacters = possibleInjectionCharacters

    @ParameterizedTest
    @ValueSource(strings = ["Musterstadt"])
    fun `valid examples`(value: String) {
        assertValid(instance(value))
    }

    @ParameterizedTest
    @ValueSource(strings = [" ", "  ", "\t", "\t\t", "\n", "\n\n"])
    fun `blank value is invalid`(value: String) {
        assertProblems(instance(value))
            .containsOnly("must not be blank")
    }

    @Test
    fun `value of max length is valid`() {
        assertValid(instance(createStringOfLength(maxLength)))
    }

    @Test
    fun `value of more than max length is invalid`() {
        assertProblems(instance(createStringOfLength(maxLength + 1)))
            .containsOnly("size must be between 0 and $maxLength")
    }

    @TestFactory
    fun `values containing any illegal characters are invalid`(): List<DynamicTest> =
        illegalCharacters.map { illegalCharacter ->
            dynamicTest("$illegalCharacter") {
                assertProblems(instance("Muster${illegalCharacter}stadt"))
                    .containsOnly("contains one of these illegal characters: $ < > \" ; '")
            }
        }

    private fun instance(value: String) = TestClass(value)

    data class TestClass(@field:City val value: String)

}


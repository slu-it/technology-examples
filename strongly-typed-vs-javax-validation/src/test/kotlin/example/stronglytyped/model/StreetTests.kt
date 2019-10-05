package example.stronglytyped.model

import example.createStringOfLength
import example.stronglytyped.validation.possibleInjectionCharacters
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class StreetTests {

    val maxLength = 100
    val illegalCharacters = possibleInjectionCharacters

    @ParameterizedTest
    @ValueSource(strings = ["Musterstraße 132 A"])
    fun `valid examples`(value: String) {
        instance(value)
    }

    @ParameterizedTest
    @ValueSource(strings = [" ", "  ", "\t", "\t\t", "\n", "\n\n"])
    fun `blank value is invalid`(value: String) {
        assertThatThrownBy { instance(value) }
            .hasMessageContaining("must not be blank")
    }

    @Test
    fun `value of max length is valid`() {
        instance(createStringOfLength(maxLength))
    }

    @Test
    fun `value of more than max length is invalid`() {
        assertThatThrownBy { instance(createStringOfLength(maxLength + 1)) }
            .hasMessageContaining("must not exceed $maxLength characters")
    }

    @TestFactory
    fun `values containing any illegal characters are invalid`(): List<DynamicTest> =
        illegalCharacters.map { illegalCharacter ->
            dynamicTest("$illegalCharacter") {
                assertThatThrownBy { instance("Musterstraße 132 ${illegalCharacter}") }
                    .hasMessageContaining("must not contain any illegal characters")
            }
        }

    @Test
    fun `toString returns original value`() {
        val value = "Musterstraße 132 A"
        val instance = instance(value)
        assertThat(instance.toString()).isEqualTo(value)
    }

    private fun instance(value: String) = Street(value)

}

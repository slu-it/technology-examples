package example.model

import example.validation.ValidationException
import example.validation.possibleInjectionCharacters
import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide
import net.jqwik.api.constraints.Chars
import net.jqwik.api.constraints.StringLength
import net.jqwik.api.constraints.Whitespace
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

internal class CityPropertyTests {

    @Property
    fun `all non-blank ASCII-based values between 1 and 50 without illegal characters are valid cities`(
        @ForAll("valid city names") value: String
    ) = assertIsValid(value)

    @Provide
    fun `valid city names`(): Arbitrary<String> = Arbitraries.strings()
        .ascii()
        .withChars('ä', 'ö', 'ü', 'Ä', 'Ö', 'Ü', 'ß', '.', ',', ' ')
        .ofMinLength(1)
        .ofMaxLength(50)
        .filter { value -> value.isNotBlank() }
        .filter { value -> value.none { possibleInjectionCharacters.contains(it) } }

    @Property
    fun `all blank values are invalid`(
        @ForAll @Whitespace @StringLength(min = 0, max = 50) value: String
    ) = assertIsInvalid(value, "must not be blank!")

    @Property
    fun `all values longer than 50 characters are invalid`(
        @ForAll @Chars('a') @StringLength(min = 51) value: String
    ) = assertIsInvalid(value, "must not be longer than")

    @Property
    fun `toString always returns the string representation of the raw String value`(
        @ForAll("valid city names") value: String
    ) {
        assertThat(City(value).toString()).isEqualTo(value)
    }

    private fun assertIsValid(value: String) {
        City(value)
    }

    private fun assertIsInvalid(value: String, problemPart: String) {
        val ex = assertThrows<ValidationException> { City(value) }
        assertThat(ex.problems).hasSize(1)
        assertThat(ex.problems.first()).contains(problemPart)
    }

}

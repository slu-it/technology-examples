package example.model

import example.validation.ValidationException
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.IntRange
import net.jqwik.api.constraints.Negative
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

internal class AgePropertyTests {

    @Property
    fun `all values between 0 and 150 are valid ages`(
        @ForAll @IntRange(max = 150) value: Int
    ) = assertIsValid(value)

    @Property
    fun `negative values are invalid ages`(
        @ForAll @Negative value: Int
    ) = assertIsInvalid(value, "must be greater than or equal to")

    @Property
    fun `values greater than 150 are invalid ages`(
        @ForAll @IntRange(min = 151) value: Int
    ) = assertIsInvalid(value, "must be less than or equal to")

    @Property
    fun `toString always returns the string representation of the raw Int value`(
        @ForAll @IntRange(max = 150) value: Int
    ) {
        assertThat(Age(value).toString()).isEqualTo(value.toString())
    }

    private fun assertIsValid(value: Int) {
        Age(value)
    }

    private fun assertIsInvalid(value: Int, problemPart: String) {
        val ex = assertThrows<ValidationException> { Age(value) }
        assertThat(ex.problems).hasSize(1)
        assertThat(ex.problems.first()).contains(problemPart)
    }

}
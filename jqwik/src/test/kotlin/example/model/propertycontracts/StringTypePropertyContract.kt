package example.model.propertycontracts

import example.validation.ValidationException
import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide
import net.jqwik.api.arbitraries.StringArbitrary
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

interface StringTypePropertyContract {

    @Provide
    fun `valid values`(): Arbitrary<String>

    @Property
    fun `all valid values can be initialized`(
        @ForAll("valid values") value: String
    ) {
        assertIsValid(value)
    }

    @Property
    fun `toString always returns the original raw String value`(
        @ForAll("valid values") value: String
    ) {
        assertThat(createInstance(value).toString()).isEqualTo(value)
    }

    fun createInstance(value: String): Any

    fun assertIsValid(value: String) {
        createInstance(value)
    }

    fun assertIsInvalid(value: String, problemPart: String) {
        val ex = assertThrows<ValidationException> { createInstance(value) }
        assertThat(ex).hasMessageContaining(problemPart)
    }

    fun arbitraryStringValues(
        baseCharacterSet: StringArbitrary.() -> StringArbitrary,
        minLength: Int,
        maxLength: Int,
        allowBlanks: Boolean,
        illegalCharacters: Set<Char>
    ): Arbitrary<String> = baseCharacterSet(Arbitraries.strings())
        .ofMinLength(minLength)
        .ofMaxLength(maxLength)
        .whitespace()
        .filter { value -> allowBlanks || value.isNotBlank() }
        .filter { value -> value.none { illegalCharacters.contains(it) } }

}

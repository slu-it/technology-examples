package example.stronglytypedwithdsl.model.contracts

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal interface IsNotBlankStringTypeContract : StringTypeContract {

    @Test
    fun `instances with non-blank values can be initialized`() {
        createInstance(validExampleValues.first())
    }

    @ParameterizedTest
    @ValueSource(strings = [" ", "  ", "\t", "\t\t", "\n", "\n\n"])
    fun `instances with blank value cannot be initialized`(value: String) {
        assertThatThrownBy { createInstance(value) }
            .hasMessageContaining("must not be blank!")
    }
}

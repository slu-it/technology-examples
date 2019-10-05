package example.stronglytyped.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class AgeTests {

    val minValue = 0
    val maxValue = 150

    @ParameterizedTest
    @ValueSource(ints = [1, 10, 100])
    fun `valid examples`(value: Int) {
        instance(value)
    }

    @Test
    fun `min value Age is valid`() {
        instance(minValue)
    }

    @Test
    fun `less than min value Age is invalid`() {
        assertThatThrownBy { instance(minValue - 1) }
            .hasMessageContaining("must be greater than or equal to 0")
    }

    @Test
    fun `max value Age is valid`() {
        instance(maxValue)
    }

    @Test
    fun `greater than max value Age is invalid`() {
        assertThatThrownBy { instance(maxValue + 1) }
            .hasMessageContaining("must be less than or equal to 150")
    }

    @Test
    fun `toString returns original value's string representation`() {
        val value = 10
        val instance = instance(value)
        assertThat(instance.toString()).isEqualTo("10")
    }

    private fun instance(value: Int) = Age(value)

}

package example.javaxvalidation.validation

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class AgeTests : ValidationTest() {

    val minValue = 0
    val maxValue = 150

    @ParameterizedTest
    @ValueSource(ints = [1, 10, 100])
    fun `valid examples`(value: Int) {
        assertValid(instance(value))
    }

    @Test
    fun `min value Age is valid`() {
        assertValid(instance(minValue))
    }

    @Test
    fun `less than min value Age is invalid`() {
        assertProblems(instance(minValue - 1))
            .containsOnly("must be greater than or equal to 0")
    }

    @Test
    fun `max value Age is valid`() {
        assertValid(instance(maxValue))
    }

    @Test
    fun `greater than max value Age is invalid`() {
        assertProblems(instance(maxValue + 1))
            .containsOnly("must be less than or equal to 150")
    }

    private fun instance(value: Int) = TestClass(value)

    data class TestClass(@field:Age val value: Int)

}


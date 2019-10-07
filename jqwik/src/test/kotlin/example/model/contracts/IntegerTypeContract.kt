package example.model.contracts

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

interface IntegerTypeContract {

    val validExampleValues: Iterable<Int>

    @TestFactory
    fun `examples of valid values`(): List<DynamicTest> = validExampleValues.map { value ->
        dynamicTest("$value") { createInstance(value) }
    }

    @Test
    fun `toString returns the string representation of the original value`() {
        val value = validExampleValues.first()
        val instance = createInstance(value)
        assertThat(instance.toString()).isEqualTo(value.toString())
    }

    fun createInstance(value: Int): Any

}

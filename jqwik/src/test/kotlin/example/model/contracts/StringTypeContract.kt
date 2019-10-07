package example.model.contracts

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

interface StringTypeContract {

    val validExampleValues: Iterable<String>

    @TestFactory
    fun `examples of valid values`(): List<DynamicTest> = validExampleValues.map { value ->
        dynamicTest(value) { createInstance(value) }
    }

    @Test
    fun `toString returns the original value`() {
        val value = validExampleValues.first()
        val instance = createInstance(value)
        assertThat(instance.toString()).isEqualTo(value)
    }

    fun createInstance(value: String): Any

}

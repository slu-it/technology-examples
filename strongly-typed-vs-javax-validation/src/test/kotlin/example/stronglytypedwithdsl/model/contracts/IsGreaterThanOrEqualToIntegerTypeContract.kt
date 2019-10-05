package example.stronglytypedwithdsl.model.contracts

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal interface IsGreaterThanOrEqualToIntegerTypeContract : IntegerTypeContract {

    val minValue: Int

    @Test
    fun `instances with min-value can be initialized`() {
        createInstance(minValue)
    }

    @Test
    fun `instances with less than min-value cannot be initialized`() {
        assertThatThrownBy { createInstance(minValue - 1) }
            .hasMessageContaining("must be greater than or equal to $minValue!")
    }
}

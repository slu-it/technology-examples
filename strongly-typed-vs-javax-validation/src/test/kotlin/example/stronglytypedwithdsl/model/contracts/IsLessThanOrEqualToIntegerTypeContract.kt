package example.stronglytypedwithdsl.model.contracts

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal interface IsLessThanOrEqualToIntegerTypeContract : IntegerTypeContract {

    val maxValue: Int

    @Test
    fun `instances with max-value can be initialized`() {
        createInstance(maxValue)
    }

    @Test
    fun `instances with greater than max-value cannot be initialized`() {
        assertThatThrownBy { createInstance(maxValue + 1) }
            .hasMessageContaining("must be less than or equal to $maxValue!")
    }
}

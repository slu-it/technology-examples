package example.stronglytypedwithdsl.model

import example.stronglytypedwithdsl.model.contracts.IntegerTypeContract
import example.stronglytypedwithdsl.model.contracts.IsGreaterThanOrEqualToIntegerTypeContract
import example.stronglytypedwithdsl.model.contracts.IsLessThanOrEqualToIntegerTypeContract

internal class AgeTests : IntegerTypeContract, IsGreaterThanOrEqualToIntegerTypeContract,
    IsLessThanOrEqualToIntegerTypeContract {

    override val minValue = 0
    override val maxValue = 150
    override val validExampleValues = listOf(1, 10, 100)

    override fun createInstance(value: Int) = Age(value)

}

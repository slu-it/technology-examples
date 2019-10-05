package example.stronglytypedwithdsl.model

import example.createStringOfLength
import example.stronglytypedwithdsl.model.contracts.DoesNotContainAnyStringTypeContract
import example.stronglytypedwithdsl.model.contracts.HasMaxLengthOfStringTypeContract
import example.stronglytypedwithdsl.model.contracts.IsNotBlankStringTypeContract
import example.stronglytypedwithdsl.model.contracts.StringTypeContract
import example.stronglytypedwithdsl.validation.possibleInjectionCharacters

internal class CityTests : StringTypeContract, IsNotBlankStringTypeContract, HasMaxLengthOfStringTypeContract,
    DoesNotContainAnyStringTypeContract {

    override val maxLength = 50
    override val illegalCharacters = possibleInjectionCharacters
    override val validExampleValues = listOf("Musterstadt")

    override fun createInstanceOfLength(length: Int) = createInstance(createStringOfLength(length))
    override fun createInstanceContaining(char: Char) = createInstance("Muster${char}stadt")
    override fun createInstance(value: String) = City(value)

}

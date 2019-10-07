package example.model

import example.createStringOfLength
import example.model.contracts.DoesNotContainAnyStringTypeContract
import example.model.contracts.HasMaxLengthOfStringTypeContract
import example.model.contracts.IsNotBlankStringTypeContract
import example.model.contracts.StringTypeContract
import example.validation.possibleInjectionCharacters

internal class CityTests : StringTypeContract, IsNotBlankStringTypeContract, HasMaxLengthOfStringTypeContract,
    DoesNotContainAnyStringTypeContract {

    override val maxLength = 50
    override val illegalCharacters = possibleInjectionCharacters
    override val validExampleValues = listOf("Musterstadt")

    override fun createInstanceOfLength(length: Int) = createInstance(createStringOfLength(length))
    override fun createInstanceContaining(char: Char) = createInstance("Muster${char}stadt")
    override fun createInstance(value: String) = City(value)

}

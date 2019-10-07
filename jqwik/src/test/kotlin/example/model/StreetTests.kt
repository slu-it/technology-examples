package example.model

import example.createStringOfLength
import example.model.contracts.DoesNotContainAnyStringTypeContract
import example.model.contracts.HasMaxLengthOfStringTypeContract
import example.model.contracts.IsNotBlankStringTypeContract
import example.model.contracts.StringTypeContract
import example.validation.possibleInjectionCharacters

internal class StreetTests : StringTypeContract, IsNotBlankStringTypeContract, HasMaxLengthOfStringTypeContract,
    DoesNotContainAnyStringTypeContract {

    override val maxLength = 100
    override val illegalCharacters = possibleInjectionCharacters
    override val validExampleValues = listOf("Musterstraße 132 A")

    override fun createInstanceOfLength(length: Int) = createInstance(createStringOfLength(length))
    override fun createInstanceContaining(char: Char) = createInstance("Musterstraße 132 ${char}")
    override fun createInstance(value: String) = Street(value)

}

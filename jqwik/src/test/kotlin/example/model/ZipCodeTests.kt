package example.model

import example.createStringOfLength
import example.model.contracts.DoesNotContainAnyStringTypeContract
import example.model.contracts.HasMaxLengthOfStringTypeContract
import example.model.contracts.IsNotBlankStringTypeContract
import example.model.contracts.StringTypeContract
import example.validation.possibleInjectionCharacters

internal class ZipCodeTests : StringTypeContract, IsNotBlankStringTypeContract, HasMaxLengthOfStringTypeContract,
    DoesNotContainAnyStringTypeContract {

    override val maxLength = 15
    override val illegalCharacters = possibleInjectionCharacters
    override val validExampleValues = listOf("12345")

    override fun createInstanceOfLength(length: Int) = createInstance(createStringOfLength(length))
    override fun createInstanceContaining(char: Char) = createInstance("123${char}45")
    override fun createInstance(value: String) = ZipCode(value)

}

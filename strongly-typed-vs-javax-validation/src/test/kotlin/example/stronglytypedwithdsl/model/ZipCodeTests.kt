package example.stronglytypedwithdsl.model

import example.createStringOfLength
import example.stronglytypedwithdsl.model.contracts.DoesNotContainAnyStringTypeContract
import example.stronglytypedwithdsl.model.contracts.HasMaxLengthOfStringTypeContract
import example.stronglytypedwithdsl.model.contracts.IsNotBlankStringTypeContract
import example.stronglytypedwithdsl.model.contracts.StringTypeContract
import example.stronglytypedwithdsl.validation.possibleInjectionCharacters

internal class ZipCodeTests : StringTypeContract, IsNotBlankStringTypeContract, HasMaxLengthOfStringTypeContract,
    DoesNotContainAnyStringTypeContract {

    override val maxLength = 15
    override val illegalCharacters = possibleInjectionCharacters
    override val validExampleValues = listOf("12345")

    override fun createInstanceOfLength(length: Int) = createInstance(createStringOfLength(length))
    override fun createInstanceContaining(char: Char) = createInstance("123${char}45")
    override fun createInstance(value: String) = ZipCode(value)

}

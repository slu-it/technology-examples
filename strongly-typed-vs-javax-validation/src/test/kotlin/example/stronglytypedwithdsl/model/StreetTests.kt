package example.stronglytypedwithdsl.model

import example.createStringOfLength
import example.stronglytypedwithdsl.model.contracts.DoesNotContainAnyStringTypeContract
import example.stronglytypedwithdsl.model.contracts.HasMaxLengthOfStringTypeContract
import example.stronglytypedwithdsl.model.contracts.IsNotBlankStringTypeContract
import example.stronglytypedwithdsl.model.contracts.StringTypeContract
import example.stronglytypedwithdsl.validation.possibleInjectionCharacters

internal class StreetTests : StringTypeContract, IsNotBlankStringTypeContract, HasMaxLengthOfStringTypeContract,
    DoesNotContainAnyStringTypeContract {

    override val maxLength = 100
    override val illegalCharacters = possibleInjectionCharacters
    override val validExampleValues = listOf("Musterstraße 132 A")

    override fun createInstanceOfLength(length: Int) = createInstance(createStringOfLength(length))
    override fun createInstanceContaining(char: Char) = createInstance("Musterstraße 132 ${char}")
    override fun createInstance(value: String) = Street(value)

}

package example.stronglytypedwithdsl.model

import example.createStringOfLength
import example.stronglytypedwithdsl.model.contracts.DoesNotContainAnyStringTypeContract
import example.stronglytypedwithdsl.model.contracts.HasMaxLengthOfStringTypeContract
import example.stronglytypedwithdsl.model.contracts.IsNotBlankStringTypeContract
import example.stronglytypedwithdsl.model.contracts.StringTypeContract
import example.stronglytypedwithdsl.validation.possibleInjectionCharacters
import org.junit.jupiter.api.Test

internal class LastNameTests : StringTypeContract, IsNotBlankStringTypeContract, HasMaxLengthOfStringTypeContract,
    DoesNotContainAnyStringTypeContract {

    override val maxLength = 50
    override val illegalCharacters = possibleInjectionCharacters - '\''
    override val validExampleValues = listOf("Mustermann")

    @Test
    fun `tick character is allowed because it might be part of a name`() {
        createInstanceContaining('\'')
    }

    override fun createInstanceOfLength(length: Int) = createInstance(createStringOfLength(length))
    override fun createInstanceContaining(char: Char) = createInstance("Muster${char}mann")
    override fun createInstance(value: String) = LastName(value)

}

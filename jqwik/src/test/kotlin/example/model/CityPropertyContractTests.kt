package example.model

import example.model.propertycontracts.DoesNotContainAnyStringTypePropertyContract
import example.model.propertycontracts.HasMaxLengthOfStringTypePropertyContract
import example.model.propertycontracts.IsNotBlankStringTypePropertyContract
import example.model.propertycontracts.StringTypePropertyContract
import example.validation.possibleInjectionCharacters
import net.jqwik.api.Arbitrary
import net.jqwik.api.arbitraries.StringArbitrary

internal class CityPropertyContractTests : StringTypePropertyContract, IsNotBlankStringTypePropertyContract,
    HasMaxLengthOfStringTypePropertyContract, DoesNotContainAnyStringTypePropertyContract {

    override fun createInstance(value: String) = City(value)

    override fun `valid values`(): Arbitrary<String> = generateValues()

    override fun `too long values`(): Arbitrary<String> = generateValues(
        minLength = 51,
        maxLength = 1024
    )

    override fun `values containing illegal characters`() = generateValues(
        baseCharacterSet = { withChars(*possibleInjectionCharacters.toCharArray()) },
        illegalCharacters = emptySet()
    )

    private fun generateValues(
        baseCharacterSet: StringArbitrary.() -> StringArbitrary = { all() },
        minLength: Int = 1,
        maxLength: Int = 50,
        allowBlanks: Boolean = false,
        illegalCharacters: Set<Char> = possibleInjectionCharacters
    ) = arbitraryStringValues(baseCharacterSet, minLength, maxLength, allowBlanks, illegalCharacters)

}

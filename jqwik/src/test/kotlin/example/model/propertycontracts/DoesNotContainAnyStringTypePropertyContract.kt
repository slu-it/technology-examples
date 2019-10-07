package example.model.propertycontracts

import net.jqwik.api.Arbitrary
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide

internal interface DoesNotContainAnyStringTypePropertyContract : StringTypePropertyContract {

    @Provide
    fun `values containing illegal characters`(): Arbitrary<String>

    @Property
    fun `all values containing any illegal characters are invalid`(
        @ForAll("values containing illegal characters") value: String
    ) = assertIsInvalid(value, "contains at least one of the following illegal characters:")

}

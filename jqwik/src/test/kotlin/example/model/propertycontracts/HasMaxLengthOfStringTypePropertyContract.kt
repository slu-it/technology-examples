package example.model.propertycontracts

import net.jqwik.api.Arbitrary
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide

internal interface HasMaxLengthOfStringTypePropertyContract : StringTypePropertyContract {

    @Provide
    fun `too long values`(): Arbitrary<String>

    @Property
    fun `all values longer than max length are invalid`(
        @ForAll("too long values") value: String
    ) = assertIsInvalid(value, "must not be longer than")

}

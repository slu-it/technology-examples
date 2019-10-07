package example.model.propertycontracts

import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.Whitespace

internal interface IsNotBlankStringTypePropertyContract : StringTypePropertyContract {

    @Property
    fun `all blank values are invalid`(
        @ForAll @Whitespace value: String
    ) = assertIsInvalid(value, "must not be blank!")

}

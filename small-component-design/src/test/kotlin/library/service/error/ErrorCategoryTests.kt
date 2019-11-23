package library.service.error

import library.service.error.ErrorCode.Category
import library.service.test.UnitTest
import library.service.test.upperCaseLetters
import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide
import org.junit.jupiter.api.assertThrows

@UnitTest
class ErrorCategoryTests {

    @Property
    fun `valid error categories can be created`(
        @ForAll("valid error categories") value: String
    ) = creationSucceeds(value)

    @Provide
    fun `valid error categories`(): Arbitrary<String> = Arbitraries.strings()
        .withChars(*upperCaseLetters)
        .ofLength(2)

    @Property
    fun `invalid error categories by characters cannot be created`(
        @ForAll("invalid error categories by chars") value: String
    ) = creationThrowsException(value)

    @Provide
    fun `invalid error categories by chars`(): Arbitrary<String> = Arbitraries.strings()
        .all()
        .ofLength(2)
        .filter { charSequence -> charSequence.none { char -> upperCaseLetters.contains(char) } }

    @Property
    fun `invalid error categories by length cannot be created`(
        @ForAll("invalid error categories by length") value: String
    ) = creationThrowsException(value)

    @Provide
    fun `invalid error categories by length`(): Arbitrary<String> = Arbitraries.strings()
        .withChars(*upperCaseLetters)
        .filter { it.length != 2 }

    private fun creationThrowsException(value: String) {
        assertThrows<IllegalArgumentException> { Category(value) }
    }

    private fun creationSucceeds(value: String) {
        Category(value)
    }

}

package library.service.error

import library.service.error.ErrorCode.Number
import library.service.test.UnitTest
import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide
import org.junit.jupiter.api.assertThrows

@UnitTest
class ErrorNumberTests {

    @Property
    fun `valid error numbers can be created`(
        @ForAll("valid error numbers") value: Int
    ) = creationSucceeds(value)

    @Provide
    fun `valid error numbers`(): Arbitrary<Int> = Arbitraries.integers()
        .between(0, 9999)

    @Property
    fun `invalid error numbers cannot be created`(
        @ForAll("invalid error numbers") value: Int
    ) = creationThrowsException(value)

    @Provide
    fun `invalid error numbers`(): Arbitrary<Int> = Arbitraries.integers()
        .between(Int.MIN_VALUE, Int.MAX_VALUE)
        .filter { it < 0 || it > 9999 }

    private fun creationThrowsException(value: Int) {
        assertThrows<IllegalArgumentException> { Number(value) }
    }

    private fun creationSucceeds(value: Int) {
        Number(value)
    }

}

package examples.extensions.lifecycle.only_once

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * This test class has a complex structure. It is used to demonstrate that
 * it is possible to execute the `BeforeAllCallback` and `AfterAllCallback`
 * only once for the whole test class.
 *
 * @see OnlyOnceLifecycleExtension
 */
@ExtendWith(OnlyOnceLifecycleExtension::class)
class OnlyOnceTest {

    @Test
    fun `root test #1`() = println("root test #1")

    @Test
    fun `root test #2`() = println("root test #2")

    @Nested
    inner class NestedOfRoot1 {

        @Test
        fun `level 1 test #1`() = println("level 1 test #1")

        @Test
        fun `level 1 test #2`() = println("level 1 test #2")

    }

    @Nested
    inner class NestedOfRoot2 {

        @Test
        fun `level 1 test #3`() = println("level 1 test #3")

        @Test
        fun `level 1 test #4`() = println("level 1 test #4")

        @Nested
        inner class NestedOfNested2 {

            @Test
            fun `level 2 test #1`() = println("level 2 test #1")

            @Test
            fun `level 2 test #2`() = println("level 2 test #2")

        }

    }

    @Nested
    inner class NestedOfRoot3 {

        @Test
        fun `level 1 test #5`() = println("level 1 test #5")

        @Test
        fun `level 1 test #6`() = println("level 1 test #6")

    }

    @Test
    fun `root test #3`() = println("root test #3")

}

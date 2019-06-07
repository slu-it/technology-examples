package examples.extensions.resources

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ResourceHandlingExtension::class)
class ResourceHandlingTest {

    @Test
    fun `root test #1`() = println("root test #1")

    @Test
    fun `root test #2`() = println("root test #2")

    @Nested
    inner class NestedOfRoot {

        @Test
        fun `level 1 test #3`() = println("level 1 test #3")

        @Test
        fun `level 1 test #4`() = println("level 1 test #4")

        @Nested
        inner class NestedOfNested {

            @Test
            fun `level 2 test #1`() = println("level 2 test #1")

            @Test
            fun `level 2 test #2`() = println("level 2 test #2")

        }

    }

    @Test
    fun `root test #3`() = println("root test #3")

}

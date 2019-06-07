package examples.extensions.lifecycle.complete

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith

/**
 * This test class has a complex structure. It is used to demonstrate in which
 * order JUnit 5's lifecycle methods are called, including the ones form
 * extensions.
 *
 * It is especially interesting that `BeforeAllCallback` and `AfterAllCallback`
 * are invoked more often then one would assume.
 */
@TestInstance(PER_CLASS)
@ExtendWith(LifecycleExtension::class)
class LifecycleWithExtensionTest {

    @BeforeAll
    fun rootBeforeAll() = println("root before all")

    @BeforeEach
    fun rootBeforeEach() = println("root before each")

    @AfterEach
    fun rootAfterEach() = println("root after each")

    @AfterAll
    fun rootAfterAll() = println("root after all")

    @Test
    fun `root test #1`() = println("root test #1")

    @Test
    fun `root test #2`() = println("root test #2")

    @Nested
    @TestInstance(PER_CLASS)
    inner class NestedOfRoot1 {

        @Test
        fun `level 1 test #1`() = println("level 1 test #1")

        @Test
        fun `level 1 test #2`() = println("level 1 test #2")

    }

    @Nested
    @TestInstance(PER_CLASS)
    inner class NestedOfRoot2 {

        @Test
        fun `level 1 test #3`() = println("level 1 test #3")

        @Test
        fun `level 1 test #4`() = println("level 1 test #4")

        @Nested
        @TestInstance(PER_CLASS)
        inner class NestedOfNested2 {

            @BeforeAll
            fun nestedLevel2BeforeAll() = println("nested level 2 before all")

            @BeforeEach
            fun nestedLevel2BeforeEach() = println("nested level 2 before each")

            @AfterEach
            fun nestedLevel2AfterEach() = println("nested level 2 after each")

            @AfterAll
            fun nestedLevel2AfterAll() = println("nested level 2 after all")

            @Test
            fun `level 2 test #1`() = println("level 2 test #1")

            @Test
            fun `level 2 test #2`() = println("level 2 test #2")

        }

    }

    @Nested
    @TestInstance(PER_CLASS)
    inner class NestedOfRoot3 {

        @BeforeAll
        fun nestedLevel1BeforeAll() = println("nested level 1 before all")

        @BeforeEach
        fun nestedLevel1BeforeEach() = println("nested Level 1 before each")

        @AfterEach
        fun nestedLevel1AfterEach() = println("nested level 1 after each")

        @AfterAll
        fun nestedLevel1AfterAll() = println("nested level 1 after all")

        @Test
        fun `level 1 test #5`() = println("level 1 test #5")

        @Test
        fun `level 1 test #6`() = println("level 1 test #6")

    }

    @Test
    fun `root test #3`() = println("root test #3")

}

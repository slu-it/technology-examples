package examples.extensions

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.*

class LifecycleExtension :
    BeforeAllCallback,
    BeforeEachCallback,
    BeforeTestExecutionCallback,
    AfterTestExecutionCallback,
    AfterEachCallback,
    AfterAllCallback {

    override fun beforeAll(context: ExtensionContext) = report("beforeAll")
    override fun beforeEach(context: ExtensionContext) = report("beforeEach")
    override fun beforeTestExecution(context: ExtensionContext) = report("beforeTestExecution")
    override fun afterTestExecution(context: ExtensionContext) = report("afterTestExecution")
    override fun afterEach(context: ExtensionContext) = report("afterEach")
    override fun afterAll(context: ExtensionContext) = report("afterAll")

    private fun report(msg: String) = report(this, msg)

}

@ExtendWith(LifecycleExtension::class)
class LifecycleTest {

    @Test fun `root test #1`() = report("root test #1")
    @Test fun `root test #2`() = report("root test #2")

    @Nested inner class NestedOfRoot1 {
        @Test fun `level 1 test #1`() = report("level 1 test #1")
        @Test fun `level 1 test #2`() = report("level 1 test #2")
    }

    @Nested inner class NestedOfRoot2 {

        @Test fun `level 1 test #3`() = report("level 1 test #3")
        @Test fun `level 1 test #4`() = report("level 1 test #4")

        @Nested inner class NestedOfNested2 {
            @Test fun `level 2 test #1`() = report("level 2 test #1")
            @Test fun `level 2 test #2`() = report("level 2 test #2")
        }

    }

    @Nested inner class NestedOfRoot3 {
        @Test fun `level 1 test #5`() = report("level 1 test #5")
        @Test fun `level 1 test #6`() = report("level 1 test #6")
    }

    @Test fun `root test #3`() = report("root test #3")

    private fun report(msg: String) = report(this, msg)

}

private fun report(caller: Any, msg: String) {
    println("[EX: $caller]: $msg")
}
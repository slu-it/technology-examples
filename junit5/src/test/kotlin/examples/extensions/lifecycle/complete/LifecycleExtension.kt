package examples.extensions.lifecycle.complete

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext

class LifecycleExtension : BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback,
    AfterTestExecutionCallback, AfterEachCallback, AfterAllCallback {

    override fun beforeAll(context: ExtensionContext) = println("extension before all")
    override fun beforeEach(context: ExtensionContext) = println("extension before each")
    override fun beforeTestExecution(context: ExtensionContext) = println("extension before test execution")
    override fun afterTestExecution(context: ExtensionContext) = println("extension after test execution")
    override fun afterEach(context: ExtensionContext) = println("extension after each")
    override fun afterAll(context: ExtensionContext) = println("extension after all")

}

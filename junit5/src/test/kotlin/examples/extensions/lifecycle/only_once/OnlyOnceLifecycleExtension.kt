package examples.extensions.lifecycle.only_once

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * This extension invokes its [BeforeAllCallback] and [AfterAllCallback] exactly
 * once. Regardless of the test class's structure (e.g. nested tests).
 */
class OnlyOnceLifecycleExtension : BeforeAllCallback, AfterAllCallback {

    override fun beforeAll(context: ExtensionContext) {
        if (isTopClassContext(context)) {
            println("beforeAll")
        }
    }

    override fun afterAll(context: ExtensionContext) {
        if (isTopClassContext(context)) {
            println("afterAll")
        }
    }

    /**
     * This method ca be used to determine whether or not the given context
     * is the top most test context ("Test Class Level"). The root context
     * is linked to JUnit's engine.
     *
     * Since that might change in the future, this solution is not a perfect
     * one. But it might be the only one currently available.
     */
    private fun isTopClassContext(context: ExtensionContext) = context.parent.orElse(null) == context.root

}

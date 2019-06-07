package examples.extensions.resources

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Namespace.*
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource

/**
 * This extension demonstrates how to initialize a resource once per test class
 * and delegate the closing / stopping of that resource to JUnit.
 *
 * Any object that implements [CloseableResource] and is stored in the context
 * will automatically be closed when the context is torn down.
 */
class ResourceHandlingExtension : BeforeAllCallback {

    override fun beforeAll(context: ExtensionContext) {
        if (context.resource == null) {
            val resource = SomeResource()
                .apply { start() }
            context.resource = resource
        }
    }

    private var ExtensionContext.resource: SomeResource?
        get() = getStore(GLOBAL).get("SOME_RESOURCE", SomeResource::class.java)
        set(value) = getStore(GLOBAL).put("SOME_RESOURCE", value)

}

class SomeResource : CloseableResource {

    fun start() {
        println("Resource started")
    }

    fun stop() {
        println("Resource stopped")
    }

    override fun close() {
        stop()
        println("Resource closed")
    }

}

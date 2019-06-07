package example

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Namespace.*
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource
import org.testcontainers.containers.GenericContainer

class ContainerizedRedis : BeforeAllCallback {

    override fun beforeAll(context: ExtensionContext) {
        if (context.container == null) {
            val redis = RedisContainer()
                .apply { start() }
            context.container = redis
            System.setProperty("REDIS_PORT", "${redis.getMappedPort(6379)}")
        }
    }

    private var ExtensionContext.container: RedisContainer?
        get() = getStore(GLOBAL).get("REDIS_CONTAINER", RedisContainer::class.java)
        set(value) = getStore(GLOBAL).put("REDIS_CONTAINER", value)

}

private class RedisContainer : GenericContainer<RedisContainer>("redis:5.0"), CloseableResource {

    init {
        addExposedPort(6379)
    }

}

package examples

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory.getLogger
import kotlin.random.Random

private val log = getLogger("async-execution")

/**
 * "Real" async execution depends on the provided context!
 *
 * Observed behavior:
 *
 * - [Default] dispatcher provides real asynchronous behavior
 * - [IO] dispatcher provides real asynchronous behavior and is a special limited [Default] dispatcher
 * - [Unconfined] does _not_ guarantee asynchronous execution
 */
fun main() {
    runBlocking {
        listOf(Default, IO, Unconfined)
            .forEach { dispatcher ->
                println()
                asyncExecution(false, dispatcher)
                println()
                asyncExecution(true, dispatcher)
                println()
            }
    }
}

private suspend fun asyncExecution(await: Boolean, dispatcher: CoroutineDispatcher) {
    log.info("// about to start async executions in different context [await: $await] //")
    withContext(dispatcher) {
        log.info("// start of dispatcher [$dispatcher] block //")
        val blocks = listOf(
            async { longRunning("#1") },
            async { longRunning("#2") },
            async { longRunning("#3") },
            async { longRunning("#4") },
            async { longRunning("#5") }
        )
        if (await) {
            blocks.forEach { it.await() }
        }
        log.info("// end of dispatcher [$dispatcher] block //")
    }
    log.info("// all async executions in different context finished //")
}

private fun longRunning(label: String) {
    val waitTime = Random.nextLong(1_000)
    log.info("// $label - start (wait: $waitTime)")
    Thread.sleep(waitTime)
    log.info("// $label - end")
}

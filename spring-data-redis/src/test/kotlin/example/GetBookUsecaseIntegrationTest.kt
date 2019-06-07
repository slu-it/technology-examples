package example

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

@ExtendWith(ContainerizedRedis::class)
@SpringBootTest(
    properties = ["spring.redis.port=\${REDIS_PORT}"]
)
class GetBookUsecaseIntegrationTest(
    @Autowired val usecase: GetBookUsecase
) {

    @Test
    fun `usecase uses cached result, does not cache null and salt is part of key`() {

        repeat(5) {
            measure { usecase.get("978-0132350884", "s1") }
            measure { usecase.get("978-0132350884", "s2") }
        }

        repeat(5) {
            measure { usecase.get("978-0137081073", "s1") }
            measure { usecase.get("978-0137081073", "s2") }
            measure { usecase.get("978-0137081073", "s3") }
        }

        repeat(3) {
            measure { assertThrows<BookNotFoundException> { usecase.get("unknown", "s") } }
        }

        // This will produce a Redis DB with the following values:

        // book-cache::SimpleKey [978-0137081073,s3]
        // book-cache::SimpleKey [978-0137081073,s2]
        // book-cache::SimpleKey [978-0132350884,s2]
        // book-cache::SimpleKey [978-0137081073,s1]
        // book-cache::SimpleKey [978-0132350884,s1]

        println("Redis Port: " + System.getProperty("REDIS_PORT"))
        println("BREAK_POINT")
    }

    fun measure(block: () -> Unit) {
        val start = System.nanoTime()
        block()
        val end = System.nanoTime()
        val duration = Duration.ofNanos(end - start).toMillis()
        println("took: ${duration}ms")
    }

}

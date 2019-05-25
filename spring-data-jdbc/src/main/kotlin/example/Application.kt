package example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class SpringDataJdbcApplication

fun main(args: Array<String>) {
    runApplication<SpringDataJdbcApplication>(*args)
}

package springsecurity

import org.springframework.stereotype.Component
import java.util.*

@Component
class IdGenerator {
    fun generate(): UUID = UUID.randomUUID()
}

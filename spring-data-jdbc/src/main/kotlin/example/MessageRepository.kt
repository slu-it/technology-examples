package example

import org.springframework.data.repository.CrudRepository
import java.util.*

interface MessageRepository : CrudRepository<Message, UUID>

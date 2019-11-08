package springsecurity

import org.springframework.stereotype.Component
import java.util.*

@Component
class BookRepository {

    private val database: MutableMap<UUID, BookRecord> = mutableMapOf()

    fun create(record: BookRecord): BookRecord {
        val id = record.id
        if (database.containsKey(id)) {
            error("record $id already exits")
        }
        database[id] = record
        return record
    }

    fun findById(id: UUID): BookRecord? {
        return database[id]
    }

    fun delete(id: UUID): Boolean {
        return database.remove(id) != null
    }

}

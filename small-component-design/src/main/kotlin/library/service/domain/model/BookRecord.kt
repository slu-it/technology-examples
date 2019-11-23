package library.service.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("book-collection")
data class BookRecord(
    @Id val id: UUID,
    val book: Book,
    val state: RecordState,
    val created: CreationData,
    val lastUpdated: UpdateData
)

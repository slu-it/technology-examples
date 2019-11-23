package library.service.domain.model

data class BookRecordPage(
    val content: List<BookRecord> = emptyList(),
    val pageNumber: Int = 0,
    val pageSize: Int = 100,
    val totalPages: Int = 1,
    val totalElements: Long = 0,
    val firstPage: Boolean = true,
    val lastPage: Boolean = true
)

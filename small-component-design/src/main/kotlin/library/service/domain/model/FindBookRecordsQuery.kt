package library.service.domain.model

data class FindBookRecordsQuery(
    val title: String? = null,
    val pageNumber: Int = 0,
    val pageSize: Int = 100
)

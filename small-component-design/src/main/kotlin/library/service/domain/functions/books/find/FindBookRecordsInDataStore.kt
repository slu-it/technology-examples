package library.service.domain.functions.books.find

import library.service.domain.stereotypes.TechnicalFunction
import library.service.domain.functions.books.BookRecordRepository
import library.service.domain.model.BookRecord
import library.service.domain.model.BookRecordPage
import library.service.domain.model.FindBookRecordsQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

@TechnicalFunction
class FindBookRecordsInDataStore(
    private val repository: BookRecordRepository
) {

    operator fun invoke(query: FindBookRecordsQuery): BookRecordPage {
        val pageable = PageRequest.of(query.pageNumber, query.pageSize, Sort.Direction.ASC, "book.title.value")
        if (query.title != null) {
            return repository.findByTitle(query.title, pageable).toBookRecordPage()
        }
        return repository.findAll(pageable).toBookRecordPage()
    }

    private fun Page<BookRecord>.toBookRecordPage() = BookRecordPage(
        content = content,
        pageNumber = number,
        pageSize = size,
        totalPages = totalPages,
        totalElements = totalElements,
        firstPage = isFirst,
        lastPage = isLast
    )
}

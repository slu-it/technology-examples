package library.service.domain.functions.books.find

import library.service.domain.model.BookRecordPage
import library.service.domain.model.BookRecordResource
import library.service.domain.model.FindBookRecordsQuery
import library.service.domain.model.toResource
import library.service.domain.stereotypes.HttpAdapter
import org.springframework.hateoas.Link
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.BasicLinkBuilder.linkToCurrentMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@HttpAdapter
@RequestMapping("/api/books")
class FindBookRecordsInCollectionHttpAdapter(
    private val findBookRecordsInCollection: FindBookRecordsInCollection
) {

    @GetMapping
    fun get(
        @RequestParam(required = false) title: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "100") size: Int
    ): PagedModel<BookRecordResource> {
        val query = FindBookRecordsQuery(title = title, pageNumber = page, pageSize = size)
        return findBookRecordsInCollection(query).toResource(title)
    }

    private fun BookRecordPage.toResource(title: String?): PagedModel<BookRecordResource> {
        val pageContent = content.map { it.toResource() }
        val pageMetaData = toPageMetaData()
        return PagedModel(pageContent, pageMetaData)
            .apply {
                add(pageLink("self", title, pageNumber, pageSize))
                addIf(!firstPage) { pageLink("previous-page", title, pageNumber - 1, pageSize) }
                addIf(!lastPage) { pageLink("next-page", title, pageNumber + 1, pageSize) }
            }
    }

    private fun BookRecordPage.toPageMetaData() =
        PagedModel.PageMetadata(pageSize.toLong(), pageNumber.toLong(), totalElements, totalPages.toLong())

    private fun pageLink(rel: String, title: String?, page: Int, size: Int): Link {
        val pageProperty = "page=$page"
        val sizeProperty = "&size=$size"
        val optionalTitleProperty = title?.let { "&title=$title" } ?: ""
        val parameters = "$pageProperty$sizeProperty$optionalTitleProperty"
        return linkToCurrentMapping().slash("api/books?$parameters").withRel(rel)
    }

}

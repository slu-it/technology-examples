package library.service.domain.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import org.springframework.hateoas.server.mvc.BasicLinkBuilder.linkToCurrentMapping

@JsonInclude(NON_NULL)
@Relation(value = "book", collectionRelation = "books")
data class BookRecordResource(
    val isbn: Isbn,
    val title: Title,
    val borrowed: Borrowed?
) : RepresentationModel<BookRecordResource>()

fun BookRecord.toResource(): BookRecordResource {
    val resource = BookRecordResource(
        isbn = this.book.isbn,
        title = this.book.title,
        borrowed = this.state.let { state -> if (state is Borrowed) state else null }
    )
    resource.add(linkToCurrentMapping().slash("api/books/${this.id}").withSelfRel())
    return resource
}

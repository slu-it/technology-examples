package springsecurity

import javax.annotation.security.RolesAllowed

@Usecase
class AddBookUsecase(
    private val repository: BookRepository,
    private val idGenerator: IdGenerator
) {

    @RolesAllowed(CURATOR)
    operator fun invoke(book: Book): BookRecord {
        val record = BookRecord(
            id = idGenerator.generate(),
            book = book
        )
        return repository.create(record)
    }

}

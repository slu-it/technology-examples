package springsecurity

import org.springframework.security.access.prepost.PreAuthorize
import java.util.*

@Usecase
class GetBookByIdUsecase(
    private val repository: BookRepository
) {

    @PreAuthorize("hasRole('$USER')")
    operator fun invoke(id: UUID): BookRecord? {
        return repository.findById(id)
    }

}

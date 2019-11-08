package springsecurity

import org.springframework.security.access.annotation.Secured
import java.util.*

@Usecase
class DeleteBookUsecase(
    private val repository: BookRepository
) {

    @Secured(ROLE_CURATOR)
    operator fun invoke(id: UUID): Boolean {
        return repository.delete(id)
    }

}

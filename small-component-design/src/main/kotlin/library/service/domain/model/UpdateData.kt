package library.service.domain.model

import java.time.Instant

data class UpdateData(
    val at: Instant,
    val by: UserName
)

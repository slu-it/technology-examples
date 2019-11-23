package library.service.domain.model

import java.time.Instant

data class CreationData(
    val at: Instant,
    val by: UserName
)

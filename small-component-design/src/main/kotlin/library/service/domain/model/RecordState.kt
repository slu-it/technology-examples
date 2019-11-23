package library.service.domain.model

import java.time.Instant

sealed class RecordState
data class Available(val since: Instant) : RecordState()
data class Borrowed(val by: Borrower, val on: Instant) : RecordState()

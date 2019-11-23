package library.service.domain.events

import library.service.domain.model.UserName
import org.springframework.context.ApplicationEvent
import java.time.Instant
import java.util.*

abstract class BusinessFunctionEvent : ApplicationEvent(Unit) {
    abstract val id: UUID
    abstract val userName: UserName
    abstract val timestamp: Instant
}

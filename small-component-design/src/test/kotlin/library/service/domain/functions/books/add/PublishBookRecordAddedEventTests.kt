package library.service.domain.functions.books.add

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import library.service.domain.events.BookRecordAdded
import library.service.domain.model.userName
import library.service.test.BookRecords.GAME_OF_THRONES
import library.service.test.TechnologyIntegrationTest
import library.service.test.UnitTest
import library.service.test.instant
import library.service.test.sequentialClock
import library.service.test.sequentialIdGenerator
import library.service.test.uuid
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationListener

internal class PublishBookRecordAddedEventTests {

    val exampleEvent = BookRecordAdded(
        id = uuid("d1789b65-fc5c-4020-b3a4-30a5bc769c6a"),
        userName = userName("slu"),
        timestamp = instant("2019-12-08T12:34:56.789Z"),
        bookRecord = GAME_OF_THRONES
    )

    @Nested
    @UnitTest
    inner class FunctionalTests {

        val eventPublisher: ApplicationEventPublisher = mockk(relaxed = true)
        val publishBookRecordAddedEvent = PublishBookRecordAddedEvent(
            eventPublisher = eventPublisher,
            idGenerator = sequentialIdGenerator("d1789b65-fc5c-4020-b3a4-30a5bc769c6a"),
            userContext = mockk { every { getCurrentUserName() } returns userName("slu") },
            clock = sequentialClock("2019-12-08T12:34:56.789Z")
        )

        @Test
        fun `invocation publishes correct event`() {
            publishBookRecordAddedEvent(GAME_OF_THRONES)
            verify { eventPublisher.publishEvent(exampleEvent) }
        }

    }

    @Nested
    @TechnologyIntegrationTest
    @SpringBootTest(classes = [Unit::class])
    inner class SpringIntegrationTests(
        @Autowired val eventPublisher: ApplicationEventPublisher
    ) {

        @MockkBean(relaxed = true)
        lateinit var eventListener: ApplicationListener<BookRecordAdded>

        @Test
        fun `event can be published via Spring's event publishing`() {
            eventPublisher.publishEvent(exampleEvent)
            verify { eventListener.onApplicationEvent(exampleEvent) }
        }

    }

}

package example

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import

@JdbcTest
@Import(CustomJdbcConfiguration::class)
internal class MessageRepositoryTests(
    @Autowired val cut: MessageRepository
) {

    @Test
    fun `saving a new message automatically generates an ID`() {
        val message = Message(sender = "sender", receiver = "receiver", content = "Hello World!")

        with(cut.save(message)) {
            assertThat(id).isNotNull()
            assertThat(sender).isEqualTo("sender")
            assertThat(receiver).isEqualTo("receiver")
            assertThat(content).isEqualTo("Hello World!")
        }
    }

    @Test
    fun `each new message gets a new ID`() {
        val id1 = cut.save(Message(sender = "foo", receiver = "bar", content = "Hello Bar!")).id
        val id2 = cut.save(Message(sender = "bar", receiver = "foo", content = "Hello Foo!")).id

        assertThat(id1).isNotNull().isNotEqualTo(id2)
        assertThat(id2).isNotNull().isNotEqualTo(id1)
    }

    @Test
    fun `saved messages can be retried by their generated ID`() {
        val message = Message(sender = "sender", receiver = "receiver", content = "Hello World!")

        val savedMessage = cut.save(message)
        val foundMessage = cut.findById(savedMessage.id!!).get()

        assertThat(foundMessage).isEqualTo(savedMessage)
        assertThat(foundMessage).isNotSameAs(savedMessage)
    }

}

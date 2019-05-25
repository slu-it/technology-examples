package example

import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.jdbc.repository.config.JdbcConfiguration
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent
import java.sql.Clob
import java.util.*

@Configuration
@EnableJdbcRepositories
class CustomJdbcConfiguration : JdbcConfiguration() {

    /**
     * Currently Spring Data JDBC does not generate IDs out of the box.
     * This bean will generate an [UUID] whenever an entity is saved without
     * an ID.
     *
     * Setting the ID property manually before calling the repository's `save`
     * method will actually not save anything at all. Spring Data JDBC assumes
     * that it should `UPDATE` a record whenever an ID is present at `save`
     * invocation time hence no data is `INSERTed` at all.
     *
     * If there are multiple entity classes which need an ID this could be
     * simplified by introducing a trait interface `HasIdentity` and checking
     * for that instead of the actual entity class.
     */
    @Bean
    fun setIdBeforeSaveEventListener() = ApplicationListener<BeforeSaveEvent> {
        val entity = it.entity
        if (entity is Message && entity.id == null) {
            entity.id = UUID.randomUUID()
        }
    }

    /**
     * Currently Spring Data JDBC does not support all SQL types. With this
     * definition the default is extended with custom converters.
     */
    override fun jdbcCustomConversions() = JdbcCustomConversions(listOf(ClobToStringConverter()))

    /**
     * Currently Spring Data JDBC does not yet support [Clob] types as Strings.
     * This [Converter] adds that capability.
     */
    private class ClobToStringConverter : Converter<Clob, String> {
        override fun convert(source: Clob): String = source.characterStream.use { it.readText() }
    }

}


package example.javaxvalidation.validation

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.IterableAssert
import javax.validation.Validation

abstract class ValidationTest {

    private val validatorFactory = Validation.buildDefaultValidatorFactory()

    protected fun assertValid(instance: Any) {
        assertThat(validate(instance)).isEmpty()
    }

    protected fun assertProblems(instance: Any): IterableAssert<String> {
        return assertThat(validate(instance))
    }

    protected fun validate(instance: Any): Iterable<String> =
        validatorFactory.validator.validate(instance).map { it.message }

}

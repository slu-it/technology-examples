package example.javaxvalidation.validation

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

internal val possibleInjectionCharacters = setOf('$', '<', '>', '"', ';', '\'') // not complete ...

@Constraint(validatedBy = [DoesNotContainAnyPossibleInjectionCharacters.Validator::class])
annotation class DoesNotContainAnyPossibleInjectionCharacters(
    val whitelist: CharArray = [],
    val message: String = "Invalid First Name",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
) {

    class Validator : ConstraintValidator<DoesNotContainAnyPossibleInjectionCharacters, CharSequence?> {

        private lateinit var illegalCharacters: Set<Char>

        override fun initialize(constraintAnnotation: DoesNotContainAnyPossibleInjectionCharacters) {
            illegalCharacters = possibleInjectionCharacters - constraintAnnotation.whitelist.toSet()
        }

        override fun isValid(value: CharSequence?, context: ConstraintValidatorContext): Boolean {
            if (value containsAny illegalCharacters) {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate(message()).addConstraintViolation()
                return false
            }
            return true
        }

        private infix fun CharSequence?.containsAny(characters: Iterable<Char>): Boolean =
            this?.any { char -> characters.contains(char) } ?: false

        private fun message() =
            "contains one of these illegal characters: ${illegalCharacters.joinToString(separator = " ")}"

    }

}

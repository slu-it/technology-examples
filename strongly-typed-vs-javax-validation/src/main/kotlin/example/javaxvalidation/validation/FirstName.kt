package example.javaxvalidation.validation

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.reflect.KClass

@NotBlank // constraint #1
@Size(max = 50) // constraint #2
@Constraint(validatedBy = [FirstName.Validator::class])
annotation class FirstName(
    val message: String = "Invalid First Name",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
) {

    class Validator : ConstraintValidator<FirstName, CharSequence?> {
        private val illegalCharacters = possibleInjectionCharacters - '\''

        override fun isValid(value: CharSequence?, context: ConstraintValidatorContext): Boolean {
            return !(value containsAny illegalCharacters) // constraint #3
        }
    }
}

package example.javaxvalidation.validation

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.reflect.KClass

@NotBlank // constraint #1
@Size(max = 15) // constraint #2
@Constraint(validatedBy = [ZipCode.Validator::class])
annotation class ZipCode(
    val message: String = "Invalid Zip Code",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
) {

    class Validator : ConstraintValidator<ZipCode, CharSequence?> {
        private val illegalCharacters = possibleInjectionCharacters

        override fun isValid(value: CharSequence?, context: ConstraintValidatorContext): Boolean {
            return !(value containsAny illegalCharacters) // constraint #3
        }
    }
}

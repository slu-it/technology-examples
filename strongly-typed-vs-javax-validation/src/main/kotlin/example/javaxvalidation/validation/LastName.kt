package example.javaxvalidation.validation

import javax.validation.Constraint
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.reflect.KClass

@NotBlank // constraint #1
@Size(max = 50) // constraint #2
@DoesNotContainAnyPossibleInjectionCharacters(whitelist = ['\'']) // constraint #3
@Constraint(validatedBy = [])
annotation class LastName(
    val message: String = "Invalid Last Name",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
)

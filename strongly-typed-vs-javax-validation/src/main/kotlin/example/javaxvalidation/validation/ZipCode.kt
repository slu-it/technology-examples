package example.javaxvalidation.validation

import javax.validation.Constraint
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.reflect.KClass

@NotBlank // constraint #1
@Size(max = 15) // constraint #2
@DoesNotContainAnyPossibleInjectionCharacters // constraint #3
@Constraint(validatedBy = [])
annotation class ZipCode(
    val message: String = "Invalid Zip Code",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
)

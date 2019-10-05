package example.javaxvalidation.validation

import javax.validation.Constraint
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import kotlin.reflect.KClass

@Min(0)
@Max(150)
@Constraint(validatedBy = [])
annotation class Age(
    val message: String = "Invalid Age",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
)

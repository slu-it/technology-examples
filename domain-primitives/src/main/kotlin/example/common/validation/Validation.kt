package example.common.validation

import example.common.validation.Validation.Companion.validate
import example.common.validation.int.isGreaterThanOrEqualTo

/**
 * DSL used to validate generic `value` instances based on a given set of
 * requirements.
 *
 * This is mostly used when defining _value types_ / _domain primitives_.
 * But it can also be used in more complex scenarios with a mix of custom
 * requirements and data classes.
 *
 * @sample integerValueExample
 * @sample customValueExample
 */
class Validation<T>(
    private val label: String,
    val value: T
) {

    private val problems: MutableList<String> = mutableListOf()

    companion object {

        fun <T> validate(value: T, label: String = dynamicName(value), block: Validation<T>.() -> Unit) =
            Validation(label, value).apply(block).validate()

        fun <T> check(value: T, label: String = dynamicName(value), block: Validation<T>.() -> Unit) =
            Validation(label, value).apply(block).check()

        private fun dynamicName(value: Any?): String =
            value?.javaClass?.simpleName ?: "unknown-type"

    }

    fun addViolation(problem: String) {
        problems.add("'$label' [$value] - $problem")
    }

    private fun validate() {
        if (problems.isNotEmpty()) throw ValidationException(label, problems)
    }

    private fun check(): List<String> = problems.toList()

}

internal fun Validation<*>.requirement(value: Boolean, descriptionSupplier: () -> String) {
    if (!value) addViolation(descriptionSupplier())
}

private fun integerValueExample() {
    validate(42, "Number") {
        isGreaterThanOrEqualTo(0) // pre-defined reusable function
        requirement((value % 2) == 0) { "must be even" }  // custom check
    }
}

private fun customValueExample() {
    data class Values(val a: Int, val b: Int)

    validate(Values(a = 5, b = 10)) {
        requirement(((value.a * value.b) % 2) == 0) { "product of a nd b must be even" }
    }
}

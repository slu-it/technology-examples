package example.stronglytypedwithdsl.validation

class Validation<T>(
    val label: String,
    val value: T
) {

    private val problems: MutableList<String> = mutableListOf()

    companion object {
        fun <T> validate(value: T, label: String, block: Validation<T>.() -> Unit) {
            Validation(label, value)
                .apply(block)
                .validateAll()
        }
    }

    fun addProblem(problem: String) {
        problems.add(problem)
    }

    private fun validateAll() {
        if (problems.isNotEmpty()) {
            throw ValidationException(label, problems)
        }
    }

}

class ValidationException(label: String, val problems: List<String>) :
    RuntimeException("Value of $label is invalid: ${problems.joinToString(separator = "; ")}")

fun Validation<Int>.isGreaterThanOrEqualTo(minValue: Int) {
    if (this.value < minValue) {
        addProblem("$label [$value] must be greater than or equal to $minValue!")
    }
}

fun Validation<Int>.isLessThanOrEqualTo(maxValue: Int) {
    if (this.value > maxValue) {
        addProblem("$label [$value] must be less than or equal to $maxValue!")
    }
}

fun Validation<String>.isNotBlank() {
    if (this.value.isBlank()) {
        addProblem("$label must not be blank!")
    }
}

fun Validation<String>.hasMaxLengthOf(maxLength: Int) {
    if (this.value.length > maxLength) {
        addProblem("$label [$value] must not be longer than $maxLength characters, but is ${value.length} characters long!")
    }
}

fun Validation<String>.doesNotContainAny(characters: Iterable<Char>) {
    if (this.value.any { char -> characters.contains(char) }) {
        val chars = characters.joinToString(separator = " ")
        addProblem("$label [$value] contains at least one of the following illegal characters: $chars")
    }
}

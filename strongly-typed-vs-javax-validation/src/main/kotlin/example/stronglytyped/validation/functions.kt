package example.stronglytyped.validation

fun validate(expression: Boolean, messageSupplier: () -> String) {
    if (!expression) throw ValidationException(messageSupplier())
}

internal infix fun CharSequence?.doesNotContainsAny(characters: Iterable<Char>): Boolean {
    return this?.none { char -> characters.contains(char) } ?: true
}

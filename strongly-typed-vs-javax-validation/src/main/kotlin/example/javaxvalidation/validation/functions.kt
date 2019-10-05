package example.javaxvalidation.validation

internal infix fun CharSequence?.containsAny(characters: Iterable<Char>): Boolean {
    return this?.any { char -> characters.contains(char) } ?: false
}

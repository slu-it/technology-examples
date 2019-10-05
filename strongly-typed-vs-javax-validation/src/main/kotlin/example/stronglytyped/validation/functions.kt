package example.stronglytyped.validation

internal infix fun CharSequence?.doesNotContainsAny(characters: Iterable<Char>): Boolean {
    return this?.none { char -> characters.contains(char) } ?: true
}

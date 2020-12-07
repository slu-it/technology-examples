package example.common.validation.string

import example.common.validation.Validation
import example.common.validation.requirement

/**
 * Requires that the _value_ is not blank (not empty and not just whitespace
 * characters).
 */
fun Validation<String>.isNotBlank() =
    requirement(value.isNotBlank()) { "must not be blank!" }

/**
 * Requires that the _value_ matches the given regular expression.
 */
fun Validation<String>.matchesPattern(pattern: Regex) =
    requirement(value.matches(pattern)) { "must match pattern: $pattern" }

const val DEFAULT_MAX_LENGTH = 255

/**
 * Requires that the _value's_ length is not greater than the given _max_ length.
 */
fun Validation<String>.hasMaxLength(maxLength: Int = DEFAULT_MAX_LENGTH) =
    requirement(value.length <= maxLength) {
        "must not be longer than $maxLength characters, but is ${value.length} characters long!"
    }

/**
 * Requires that the _value_ does not contain any of the given _characters_.
 * This is basically a character blacklist.
 */
fun Validation<String>.doesNotContainAny(characters: Iterable<Char>) =
    requirement(value.none { char -> characters.contains(char) }) {
        "contains at least one of the following illegal characters: ${characters.joinToString(" ")}"
    }

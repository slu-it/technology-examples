package example.common.validation.int

import example.common.validation.Validation
import example.common.validation.requirement

/**
 * Requires that the _value_ is within the given range of integers including
 * the start and end values.
 */
fun Validation<Int>.isBetween(range: IntRange) =
    requirement(range.contains(value)) { "must be between ${range.first} and ${range.last}" }

/**
 * Requires that the _value_ is greater than or equal to the given _min_ value.
 */
fun Validation<Int>.isGreaterThanOrEqualTo(minValue: Int) =
    requirement(value >= minValue) { "must be greater than or equal to $minValue!" }

/**
 * Requires that the _value_ is less than or equal to the given _max_ value.
 */
fun Validation<Int>.isLessThanOrEqualTo(maxValue: Int) =
    requirement(value <= maxValue) { "must be less than or equal to $maxValue!" }

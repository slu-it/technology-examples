package example

fun createStringOfLength(length: Int, char: Char = 'a'): String =
    (1..length).map { char }.joinToString(separator = "")

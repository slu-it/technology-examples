package example.stronglytyped.validation

class ValidationException(msg: String, cause: Throwable? = null) : RuntimeException(msg, cause)

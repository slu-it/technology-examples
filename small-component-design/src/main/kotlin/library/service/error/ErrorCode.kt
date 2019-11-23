package library.service.error

private val CATEGORY_REGEX = Regex("[A-Z]{2}")

data class ErrorCode(
    private val category: Category = Category("UK"),
    private val number: Number = Number()
) {

    data class Category(private val value: String) {

        init {
            require(value.matches(CATEGORY_REGEX)) { "must match $CATEGORY_REGEX" }
        }

        override fun toString() = value

    }

    data class Number(private val value: Int = 0) {

        init {
            require(value in 0..9999) { "must be between 0 and 9999" }
        }

        override fun toString() = "$value".padStart(4, '0')

    }

    override fun toString() = "$category-$number"

}

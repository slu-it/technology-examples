package springsecurity

import java.util.*


val uuid = UUID.fromString("cd690768-74d4-48a8-8443-664975dd46b5")
val book = Book(
    isbn = "978-0134757599",
    title = "Refactoring: Improving the Design of Existing Code"
)
val bookRecord = BookRecord(uuid, book)

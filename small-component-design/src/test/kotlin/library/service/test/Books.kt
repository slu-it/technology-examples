package library.service.test

import library.service.domain.model.Available
import library.service.domain.model.Book
import library.service.domain.model.BookRecord
import library.service.domain.model.Borrowed
import library.service.domain.model.CreationData
import library.service.domain.model.Isbn
import library.service.domain.model.Title
import library.service.domain.model.UpdateData
import library.service.domain.model.borrower
import library.service.domain.model.userName

object Books {
    val GAME_OF_THRONES = Book(
        isbn = Isbn("978-0553573404"),
        title = Title("A Game of Thrones")
    )
    val CLASH_OF_KINGS = Book(
        isbn = Isbn("978-0553579901"),
        title = Title("A Clash of Kings")
    )
    val STORM_OF_SWORDS = Book(
        isbn = Isbn("978-0553573428"),
        title = Title("A Storm of Swords")
    )
    val FEAST_FOR_CROWS = Book(
        isbn = Isbn("978-0553582024"),
        title = Title("A Feast for Crows")
    )
    val DANCE_WITH_DRAGONS = Book(
        isbn = Isbn("978-0006486114"),
        title = Title("A Dance With Dragons")
    )
}

object BookRecords {
    val GAME_OF_THRONES = BookRecord(
        id = uuid("ee52d8e5-28c7-406b-8575-11f40475b207"),
        book = Books.GAME_OF_THRONES,
        state = Available(since = instant("2019-11-23T12:34:56.789Z")),
        created = CreationData(at = instant("2019-11-23T12:34:56.789Z"), by = userName("slu")),
        lastUpdated = UpdateData(at = instant("2019-11-23T12:34:56.789Z"), by = userName("slu"))
    )
    val GAME_OF_THRONES_BORROWED = GAME_OF_THRONES
        .copy(state = Borrowed(by = borrower("slu"), on = instant("2020-01-21T12:34:56.789Z")))
    val CLASH_OF_KINGS = BookRecord(
        id = uuid("523d4c4b-e934-4616-b1fd-d4dc82eddd8c"),
        book = Books.CLASH_OF_KINGS,
        state = Available(since = instant("2019-11-23T12:34:56.789Z")),
        created = CreationData(at = instant("2019-11-23T12:34:56.789Z"), by = userName("slu")),
        lastUpdated = UpdateData(at = instant("2019-11-23T12:34:56.789Z"), by = userName("slu"))
    )
    val STORM_OF_SWORDS = BookRecord(
        id = uuid("bbec2b7b-d77e-43c5-8874-0ca375510ecc"),
        book = Books.STORM_OF_SWORDS,
        state = Available(since = instant("2019-11-23T12:34:56.789Z")),
        created = CreationData(at = instant("2019-11-23T12:34:56.789Z"), by = userName("slu")),
        lastUpdated = UpdateData(at = instant("2019-11-23T12:34:56.789Z"), by = userName("slu"))
    )
    val FEAST_FOR_CROWS = BookRecord(
        id = uuid("25223b3c-fd0c-4af6-8d2e-9830f80b5f68"),
        book = Books.FEAST_FOR_CROWS,
        state = Available(since = instant("2019-11-23T12:34:56.789Z")),
        created = CreationData(at = instant("2019-11-23T12:34:56.789Z"), by = userName("slu")),
        lastUpdated = UpdateData(at = instant("2019-11-23T12:34:56.789Z"), by = userName("slu"))
    )
    val DANCE_WITH_DRAGONS = BookRecord(
        id = uuid("41d14c4a-0f3d-4750-84cb-ffd12b0f8ee4"),
        book = Books.DANCE_WITH_DRAGONS,
        state = Available(since = instant("2019-11-23T12:34:56.789Z")),
        created = CreationData(at = instant("2019-11-23T12:34:56.789Z"), by = userName("slu")),
        lastUpdated = UpdateData(at = instant("2019-11-23T12:34:56.789Z"), by = userName("slu"))
    )
    val DANCE_WITH_DRAGONS_BORROWED = DANCE_WITH_DRAGONS
        .copy(state = Borrowed(by = borrower("slu"), on = instant("2020-01-23T12:34:56.789Z")))
}

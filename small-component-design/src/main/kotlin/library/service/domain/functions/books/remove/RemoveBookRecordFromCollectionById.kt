package library.service.domain.functions.books.remove

import library.service.domain.stereotypes.BusinessFunction
import library.service.security.CanOnlyBeExecutedByCurators
import java.util.*

@BusinessFunction
class RemoveBookRecordFromCollectionById(
    private val removeBookRecordFromDataStoreById: RemoveBookRecordFromDataStoreById,
    private val publishBookRecordDeletedEvent: PublishBookRecordDeletedEvent
) {

    @CanOnlyBeExecutedByCurators
    operator fun invoke(id: UUID): Boolean {
        if (removeBookRecordFromDataStoreById(id)) {
            publishBookRecordDeletedEvent(id)
            return true
        }
        return false
    }

}

package library.service.domain.functions

import library.service.error.ErrorCode
import library.service.error.HasErrorCode
import org.springframework.dao.DataAccessException
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.NonTransientDataAccessResourceException
import org.springframework.dao.TransientDataAccessResourceException

@Throws(DataStoreException::class)
fun <T> dataStoreOperation(block: () -> T) = try {
    block()
} catch (e: DuplicateKeyException) {
    throw DataEntryAlreadyExistsException(e)
} catch (e: NonTransientDataAccessResourceException) {
    throw DataStoreUnavailableException(e)
} catch (e: TransientDataAccessResourceException) {
    throw DataStoreTemporarilyUnavailableException(e)
} catch (e: DataAccessException) {
    throw UnclassifiedDataStoreException(e)
}

class UnclassifiedDataStoreException(cause: Throwable) :
    DataStoreException(cause = cause, errorNumber = 0)

class DataStoreUnavailableException(cause: Throwable) :
    DataStoreException(cause = cause, errorNumber = 10)

class DataStoreTemporarilyUnavailableException(cause: Throwable) :
    DataStoreException(cause = cause, errorNumber = 12)

class DataEntryAlreadyExistsException(cause: Throwable) :
    DataStoreException(cause = cause, errorNumber = 1001)

sealed class DataStoreException(cause: Throwable? = null, errorNumber: Int) :
    RuntimeException(cause?.message, cause), HasErrorCode {
    override val errorCode = ErrorCode(ErrorCode.Category("DS"), ErrorCode.Number(errorNumber))
}

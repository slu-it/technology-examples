package library.service.domain.functions

import library.service.domain.model.UserName

interface UserContext {
    fun getCurrentUserName(): UserName
}

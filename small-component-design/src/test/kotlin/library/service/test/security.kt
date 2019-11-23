package library.service.test

import library.service.domain.functions.UserContext
import library.service.domain.model.UserName

fun fixedUserContext(userName: String): UserContext = FixedUserContext(UserName(userName))

private class FixedUserContext(val user: UserName) : UserContext {
    override fun getCurrentUserName(): UserName = user
}

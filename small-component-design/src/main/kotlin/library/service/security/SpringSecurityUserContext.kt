package library.service.security

import library.service.domain.functions.UserContext
import library.service.domain.model.UserName
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User

class SpringSecurityUserContext : UserContext {
    override fun getCurrentUserName(): UserName {
        val context = SecurityContextHolder.getContext()
        val authentication = context.authentication
        check(authentication != null) { "no active user in context" }
        val user = authentication.principal as User
        return UserName(user.username)
    }
}

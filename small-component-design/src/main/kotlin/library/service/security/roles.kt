package library.service.security

import org.springframework.security.access.prepost.PreAuthorize
import kotlin.annotation.AnnotationTarget.FUNCTION

const val USER = "USER"
const val ROLE_USER = "ROLE_$USER"
const val CURATOR = "CURATOR"
const val ROLE_CURATOR = "ROLE_$CURATOR"
const val ADMIN = "ADMIN"
const val ROLE_ADMIN = "ROLE_$ADMIN"

@Retention
@Target(FUNCTION)
@PreAuthorize("hasRole('$ROLE_USER')")
annotation class CanBeExecutedByAnyUser

@Retention
@Target(FUNCTION)
@PreAuthorize("hasRole('$ROLE_CURATOR')")
annotation class CanOnlyBeExecutedByCurators

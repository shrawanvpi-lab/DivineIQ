package com.divineiq.app.repository

import com.divineiq.app.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

/**
 * Default [AuthRepository] used until Firebase Authentication / Google
 * Sign-In is wired up. Every session is a local guest identity — fully
 * functional today, with no server round-trip and nothing to configure.
 */
class GuestAuthRepository : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: StateFlow<User?> = _currentUser

    override suspend fun signInAsGuest(): Result<User> {
        val guest = User(
            id = "guest-${UUID.randomUUID()}",
            displayName = "Guest User",
            email = null,
            isGuest = true,
            isPremium = false
        )
        _currentUser.value = guest
        return Result.success(guest)
    }

    override suspend fun signOut() {
        _currentUser.value = null
    }
}

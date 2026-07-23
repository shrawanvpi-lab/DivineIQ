package com.divineiq.app.repository

import com.divineiq.app.model.User
import kotlinx.coroutines.flow.StateFlow

/**
 * Account state for the app. [GuestAuthRepository] is the only
 * implementation today; a future Firebase-backed implementation (using
 * [com.divineiq.app.firebase.AuthProvider]) can drop in here without any
 * caller needing to change, since both would satisfy this same contract.
 */
interface AuthRepository {
    val currentUser: StateFlow<User?>
    suspend fun signInAsGuest(): Result<User>
    suspend fun signOut()
}

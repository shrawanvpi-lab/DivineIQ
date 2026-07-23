package com.divineiq.app.model

/**
 * The signed-in (or guest) identity driving DivineIQ's account state.
 * A future Firebase Authentication / Google Sign-In implementation of
 * [com.divineiq.app.repository.AuthRepository] produces the same type, so
 * the rest of the app never needs to know which auth backend is active.
 */
data class User(
    val id: String,
    val displayName: String,
    val email: String?,
    val isGuest: Boolean,
    val isPremium: Boolean = false
)

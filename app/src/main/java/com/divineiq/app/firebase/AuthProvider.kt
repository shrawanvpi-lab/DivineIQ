package com.divineiq.app.firebase

import com.divineiq.app.model.User

/**
 * Contract a future Firebase Authentication integration (email/password
 * and Google Sign-In) will implement.
 *
 * Deliberately dependency-free: no Firebase SDK is added to the Gradle
 * build until this is wired up, so adding it later is a matter of
 * implementing this interface and swapping it in via [com.divineiq.app.AppContainer]
 * — nothing else in the app depends on Firebase directly.
 */
interface AuthProvider {

    /** Exchanges a Google ID token (from Credential Manager / One Tap) for a [User]. */
    suspend fun signInWithGoogle(idToken: String): Result<User>

    suspend fun signInWithEmail(email: String, password: String): Result<User>

    suspend fun signOut()
}

package com.divineiq.app.model

/**
 * The signed-in (or guest) user's profile and lightweight usage stats.
 */
data class UserProfile(
    val name: String,
    val email: String,
    val avatarUrl: String,
    val notesCount: Int,
    val quizzesCount: Int,
    val streakDays: Int
)

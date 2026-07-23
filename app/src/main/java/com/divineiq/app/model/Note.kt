package com.divineiq.app.model

/**
 * A user-authored note.
 */
data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val createdAtMillis: Long
)

package com.divineiq.app.model

/**
 * A quiz summary shown in the Quiz list.
 */
data class Quiz(
    val id: Long,
    val title: String,
    val description: String,
    val questionCount: Int,
    val imageUrl: String
)

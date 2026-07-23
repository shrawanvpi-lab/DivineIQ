package com.divineiq.app.model

/** The kinds of content DivineIQ allows bookmarking. */
enum class BookmarkType {
    HOME_ITEM,
    QUIZ
}

/**
 * A user-saved reference to a [HomeItem] or [Quiz], for the upcoming
 * Bookmarks screen.
 */
data class Bookmark(
    val id: Long,
    val refId: Long,
    val type: BookmarkType,
    val title: String,
    val createdAtMillis: Long
)

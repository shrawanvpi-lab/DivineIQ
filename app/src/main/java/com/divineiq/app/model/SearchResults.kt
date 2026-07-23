package com.divineiq.app.model

/**
 * Aggregated results for a single search query across every local and
 * cached catalog DivineIQ knows about. Returned by
 * [com.divineiq.app.repository.SearchRepository].
 */
data class SearchResults(
    val query: String,
    val notes: List<Note>,
    val homeItems: List<HomeItem>,
    val quizzes: List<Quiz>
) {
    val isEmpty: Boolean
        get() = notes.isEmpty() && homeItems.isEmpty() && quizzes.isEmpty()
}

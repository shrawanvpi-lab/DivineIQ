package com.divineiq.app.repository

import com.divineiq.app.model.SearchResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Filters across every catalog DivineIQ has available locally: notes in
 * Room, plus whatever Home items and Quizzes were most recently fetched
 * from the network by [ContentRepository]. Backs the upcoming global
 * search entry point in the toolbar.
 */
class SearchRepository(
    private val contentRepository: ContentRepository,
    private val notesRepository: NotesRepository
) {

    suspend fun search(query: String): SearchResults = withContext(Dispatchers.Default) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            return@withContext SearchResults(trimmed, emptyList(), emptyList(), emptyList())
        }

        val matchedNotes = notesRepository.search(trimmed)
        val matchedHomeItems = contentRepository.lastHomeItems.filter {
            it.title.contains(trimmed, ignoreCase = true) ||
                it.category.contains(trimmed, ignoreCase = true)
        }
        val matchedQuizzes = contentRepository.lastQuizzes.filter {
            it.title.contains(trimmed, ignoreCase = true) ||
                it.description.contains(trimmed, ignoreCase = true)
        }

        SearchResults(
            query = trimmed,
            notes = matchedNotes,
            homeItems = matchedHomeItems,
            quizzes = matchedQuizzes
        )
    }
}

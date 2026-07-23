package com.divineiq.app.repository

import com.divineiq.app.data.local.dao.NoteDao
import com.divineiq.app.data.local.entity.NoteEntity
import com.divineiq.app.data.local.toDomain
import com.divineiq.app.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Source of truth for the user's Notes, persisted offline in Room so they
 * survive process death and are available without a network connection.
 */
class NotesRepository(private val noteDao: NoteDao) {

    fun observeNotes(): Flow<List<Note>> =
        noteDao.observeAll().map { entities -> entities.map { it.toDomain() } }

    fun observeNoteCount(): Flow<Int> = noteDao.observeCount()

    suspend fun search(query: String): List<Note> = withContext(Dispatchers.IO) {
        noteDao.search(query).map { it.toDomain() }
    }

    suspend fun addNote(title: String, content: String) = withContext(Dispatchers.IO) {
        noteDao.insert(
            NoteEntity(
                title = title.ifBlank { "Untitled" },
                content = content,
                createdAtMillis = System.currentTimeMillis()
            )
        )
        Unit
    }

    suspend fun deleteNote(noteId: Long) = withContext(Dispatchers.IO) {
        noteDao.deleteById(noteId)
    }
}

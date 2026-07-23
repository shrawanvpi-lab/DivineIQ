package com.divineiq.app.repository

import com.divineiq.app.data.local.dao.BookmarkDao
import com.divineiq.app.data.local.toDomain
import com.divineiq.app.data.local.toEntity
import com.divineiq.app.model.Bookmark
import com.divineiq.app.model.BookmarkType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Source of truth for the user's bookmarked content, persisted offline in
 * Room. Backs the upcoming Bookmarks screen.
 */
class BookmarkRepository(private val bookmarkDao: BookmarkDao) {

    fun observeBookmarks(): Flow<List<Bookmark>> =
        bookmarkDao.observeAll().map { entities -> entities.map { it.toDomain() } }

    fun observeIsBookmarked(refId: Long, type: BookmarkType): Flow<Boolean> =
        bookmarkDao.observeIsBookmarked(refId, type.name)

    suspend fun addBookmark(refId: Long, type: BookmarkType, title: String) =
        withContext(Dispatchers.IO) {
            bookmarkDao.insert(
                Bookmark(
                    id = 0L,
                    refId = refId,
                    type = type,
                    title = title,
                    createdAtMillis = System.currentTimeMillis()
                ).toEntity()
            )
        }

    suspend fun removeBookmark(refId: Long, type: BookmarkType) = withContext(Dispatchers.IO) {
        bookmarkDao.delete(refId, type.name)
    }
}

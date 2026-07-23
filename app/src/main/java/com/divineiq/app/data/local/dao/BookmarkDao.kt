package com.divineiq.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.divineiq.app.data.local.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmarks ORDER BY createdAtMillis DESC")
    fun observeAll(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE refId = :refId AND type = :type")
    suspend fun delete(refId: Long, type: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE refId = :refId AND type = :type)")
    fun observeIsBookmarked(refId: Long, type: String): Flow<Boolean>
}

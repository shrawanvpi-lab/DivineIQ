package com.divineiq.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.divineiq.app.data.local.dao.BookmarkDao
import com.divineiq.app.data.local.dao.NoteDao
import com.divineiq.app.data.local.entity.BookmarkEntity
import com.divineiq.app.data.local.entity.NoteEntity

/**
 * DivineIQ's on-device offline cache. Notes and bookmarks are stored here
 * so they survive process death and remain available without a network
 * connection; a future [com.divineiq.app.firebase.CloudSyncProvider]
 * implementation can mirror this data to Firestore for signed-in users
 * without changing how the rest of the app reads from it.
 */
@Database(
    entities = [NoteEntity::class, BookmarkEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        private const val DATABASE_NAME = "divineiq.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build().also { instance = it }
            }
    }
}

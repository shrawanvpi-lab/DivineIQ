package com.divineiq.app

import android.content.Context
import com.divineiq.app.data.local.AppDatabase
import com.divineiq.app.repository.AuthRepository
import com.divineiq.app.repository.BookmarkRepository
import com.divineiq.app.repository.ContentRepository
import com.divineiq.app.repository.GuestAuthRepository
import com.divineiq.app.repository.NotesRepository
import com.divineiq.app.repository.SearchRepository
import com.divineiq.app.repository.SettingsRepository

/**
 * Hand-rolled dependency container shared across the app.
 *
 * This is deliberately simple constructor injection rather than a
 * framework: every repository is created once here and handed to
 * ViewModels via [com.divineiq.app.viewmodel.ViewModelFactory]. If the
 * app grows enough to want compile-time graph validation, this class is
 * the single place a Hilt `@Module` would replace.
 */
class AppContainer(context: Context) {

    private val database: AppDatabase = AppDatabase.getInstance(context)

    val contentRepository: ContentRepository = ContentRepository()
    val notesRepository: NotesRepository = NotesRepository(database.noteDao())
    val bookmarkRepository: BookmarkRepository = BookmarkRepository(database.bookmarkDao())
    val settingsRepository: SettingsRepository = SettingsRepository(context.applicationContext)
    val searchRepository: SearchRepository = SearchRepository(contentRepository, notesRepository)
    val authRepository: AuthRepository = GuestAuthRepository()
}

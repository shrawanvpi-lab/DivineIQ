package com.divineiq.app.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.divineiq.app.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(name = "divineiq_settings")

/**
 * Persists lightweight user preferences (currently just [ThemeMode]) using
 * Jetpack DataStore. Ready to grow into the future Settings screen without
 * changing its public surface.
 */
class SettingsRepository(private val context: Context) {

    fun observeThemeMode(): Flow<ThemeMode> =
        context.settingsDataStore.data.map { prefs ->
            ThemeMode.fromStorageValue(prefs[THEME_MODE_KEY])
        }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.settingsDataStore.edit { prefs -> prefs[THEME_MODE_KEY] = mode.storageValue }
    }

    companion object {
        private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    }
}

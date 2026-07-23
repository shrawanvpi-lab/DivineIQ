package com.divineiq.app.model

/**
 * User-selectable app theme, persisted by
 * [com.divineiq.app.repository.SettingsRepository]. `SYSTEM` defers to the
 * device's day/night + dynamic color setting, which is DivineIQ's default.
 */
enum class ThemeMode(val storageValue: String) {
    SYSTEM("system"),
    LIGHT("light"),
    DARK("dark");

    companion object {
        fun fromStorageValue(value: String?): ThemeMode =
            entries.firstOrNull { it.storageValue == value } ?: SYSTEM
    }
}

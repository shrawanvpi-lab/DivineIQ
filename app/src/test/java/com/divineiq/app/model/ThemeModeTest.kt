package com.divineiq.app.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ThemeModeTest {

    @Test
    fun `fromStorageValue maps known values back to the right mode`() {
        assertEquals(ThemeMode.LIGHT, ThemeMode.fromStorageValue("light"))
        assertEquals(ThemeMode.DARK, ThemeMode.fromStorageValue("dark"))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromStorageValue("system"))
    }

    @Test
    fun `fromStorageValue falls back to SYSTEM for unknown or missing values`() {
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromStorageValue(null))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromStorageValue("not-a-real-mode"))
    }
}

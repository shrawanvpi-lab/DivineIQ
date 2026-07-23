package com.divineiq.app.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchResultsTest {

    @Test
    fun `isEmpty is true when every list is empty`() {
        val results = SearchResults("query", emptyList(), emptyList(), emptyList())
        assertTrue(results.isEmpty)
    }

    @Test
    fun `isEmpty is false when any single list has a match`() {
        val note = Note(id = 1L, title = "t", content = "c", createdAtMillis = 0L)
        val results = SearchResults("query", listOf(note), emptyList(), emptyList())
        assertFalse(results.isEmpty)
    }
}

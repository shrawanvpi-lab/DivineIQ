package com.divineiq.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Local (Room) representation of a bookmarked piece of content. [type]
 * distinguishes which catalog the bookmark points into (see
 * [BookmarkType]) and [refId] is that item's id within it.
 */
@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val refId: Long,
    val type: String,
    val title: String,
    val createdAtMillis: Long
)

package com.divineiq.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Local (Room) representation of a user note. This is DivineIQ's offline
 * cache for Notes — the source of truth lives on-device until a future
 * Firestore sync layer (see [com.divineiq.app.firebase.CloudSyncProvider])
 * mirrors it to the cloud for signed-in users.
 */
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val content: String,
    val createdAtMillis: Long
)

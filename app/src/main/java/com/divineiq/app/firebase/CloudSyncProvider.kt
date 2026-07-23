package com.divineiq.app.firebase

import com.divineiq.app.model.Note

/**
 * Contract a future Firestore-backed sync layer will implement to mirror
 * local Room data (starting with Notes) to the cloud for signed-in,
 * non-guest users. Dependency-free for the same reason as [AuthProvider]:
 * no Firebase SDK is added until this is actually implemented.
 */
interface CloudSyncProvider {
    suspend fun pushNotes(notes: List<Note>): Result<Unit>
    suspend fun pullNotes(): Result<List<Note>>
}

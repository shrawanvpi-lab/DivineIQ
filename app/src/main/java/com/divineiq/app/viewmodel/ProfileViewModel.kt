package com.divineiq.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divineiq.app.model.UserProfile
import com.divineiq.app.repository.ContentRepository
import com.divineiq.app.repository.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Profile tab state: static account info from [ContentRepository] combined
 * with the live notes count from Room via [NotesRepository], so the count
 * updates immediately after adding or deleting a note elsewhere in the app.
 */
class ProfileViewModel(
    contentRepository: ContentRepository,
    notesRepository: NotesRepository
) : ViewModel() {

    val profile: StateFlow<UserProfile> = notesRepository.observeNoteCount()
        .map { count -> contentRepository.getUserProfile().copy(notesCount = count) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            contentRepository.getUserProfile()
        )
}

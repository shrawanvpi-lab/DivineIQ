package com.divineiq.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.divineiq.app.AppContainer

/**
 * Builds each feature ViewModel from the shared [AppContainer]. Explicit
 * `when` branches (rather than reflection) keep constructor-signature
 * mismatches as compile errors instead of runtime crashes, at the cost of
 * one line per ViewModel added here.
 */
class ViewModelFactory(private val appContainer: AppContainer) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(appContainer.contentRepository)

            modelClass.isAssignableFrom(NotesViewModel::class.java) ->
                NotesViewModel(appContainer.notesRepository)

            modelClass.isAssignableFrom(QuizViewModel::class.java) ->
                QuizViewModel(appContainer.contentRepository)

            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(appContainer.contentRepository, appContainer.notesRepository)

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

        return viewModel as T
    }
}

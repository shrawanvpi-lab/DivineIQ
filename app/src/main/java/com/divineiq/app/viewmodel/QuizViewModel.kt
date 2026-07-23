package com.divineiq.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divineiq.app.model.Quiz
import com.divineiq.app.repository.ContentRepository
import com.divineiq.app.utils.UiState
import kotlinx.coroutines.launch

/**
 * Loads and holds the state for the Quiz tab's list of quizzes.
 */
class QuizViewModel(
    private val repository: ContentRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<List<Quiz>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<Quiz>>> = _uiState

    init {
        loadQuizzes()
    }

    fun loadQuizzes() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            repository.getQuizzes()
                .onSuccess { quizzes -> _uiState.value = UiState.Success(quizzes) }
                .onFailure { error ->
                    _uiState.value = UiState.Error(error.message ?: "Unable to load quizzes")
                }
        }
    }
}

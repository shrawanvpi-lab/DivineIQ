package com.divineiq.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divineiq.app.model.HomeItem
import com.divineiq.app.repository.ContentRepository
import com.divineiq.app.utils.UiState
import kotlinx.coroutines.launch

/**
 * Loads and holds the state for the Home screen's recommended-content list.
 */
class HomeViewModel(
    private val repository: ContentRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<List<HomeItem>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<HomeItem>>> = _uiState

    init {
        loadHomeItems()
    }

    fun loadHomeItems() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            repository.getHomeItems()
                .onSuccess { items -> _uiState.value = UiState.Success(items) }
                .onFailure { error ->
                    _uiState.value = UiState.Error(error.message ?: "Unable to load content")
                }
        }
    }
}

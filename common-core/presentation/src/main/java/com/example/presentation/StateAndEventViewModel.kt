package com.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class StateAndEventViewModel<UIState, UIEvent>(initialState: UIState) : ViewModel() {

    private val uiEvents = MutableSharedFlow<UIEvent>(replay = 0)
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<UIState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            uiEvents.collect { uiEvent ->
                handleEvent(uiEvent)
            }
        }
    }

    protected abstract suspend fun handleEvent(uiEvent: UIEvent)

    protected fun updateUIState(updateUIState: UIState.() -> UIState) {
        _uiState.update { _uiState.value.updateUIState() }
    }

    fun onUIEvent(uiEvent: UIEvent) {
        viewModelScope.launch {
            uiEvents.emit(uiEvent)
        }
    }
}
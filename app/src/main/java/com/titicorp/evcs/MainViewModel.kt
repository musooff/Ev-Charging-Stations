package com.titicorp.evcs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titicorp.evcs.domain.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    isLoggedInUseCase: IsLoggedInUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            val isLoggedIn = isLoggedInUseCase()
            _uiState.value = UiState.Success(
                isLoggedIn = isLoggedIn,
            )
        }
    }

    sealed interface UiState {

        object Loading : UiState
        data class Success(
            val isLoggedIn: Boolean,
        ) : UiState
    }
}

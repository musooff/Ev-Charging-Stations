package com.titicorp.evcs.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titicorp.evcs.domain.GetStationsUseCase
import com.titicorp.evcs.domain.GetUserNameUseCase
import com.titicorp.evcs.domain.LogOutUseCase
import com.titicorp.evcs.model.Station
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStationsUseCase: GetStationsUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val logOutUseCase: LogOutUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            val userName = getUserNameUseCase()
            val stations = getStationsUseCase()
            _uiState.value = UiState.Success(
                name = userName,
                stations = stations,
            )
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
            _uiState.value = UiState.LogOut
        }
    }

    sealed interface UiState {
        object Loading : UiState
        data class Success(
            val name: String,
            val stations: List<Station>,
        ) : UiState

        object LogOut : UiState
    }
}

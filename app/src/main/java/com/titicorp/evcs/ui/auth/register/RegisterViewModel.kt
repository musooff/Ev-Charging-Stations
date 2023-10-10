package com.titicorp.evcs.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titicorp.evcs.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private var loggingJob: Job? = null

    fun onNameChanged(value: String) {
        _uiState.update {
            it.copy(
                name = value,
            )
        }
    }

    fun onPhoneNumberChanged(value: String) {
        _uiState.update {
            it.copy(
                phoneNumber = value,
            )
        }
    }

    fun onPasswordChanged(value: String) {
        _uiState.update {
            it.copy(
                password = value,
            )
        }
    }

    fun register() {
        _uiState.update {
            it.copy(
                registerState = UiState.RegisterState.Registering,
            )
        }
        loggingJob?.cancel()
        loggingJob = viewModelScope.launch {
            val user = registerUseCase(
                name = uiState.value.name,
                phoneNumber = uiState.value.phoneNumber,
                password = uiState.value.password,
            )
            _uiState.update {
                it.copy(
                    registerState = UiState.RegisterState.LoggedIn,
                )
            }
        }
    }

    fun cancelRegister() {
        loggingJob?.cancel()
        loggingJob = null
        _uiState.update {
            it.copy(
                registerState = UiState.RegisterState.Idle,
            )
        }
    }

    data class UiState(
        val name: String = "",
        val phoneNumber: String = "",
        val password: String = "",
        val registerState: RegisterState = RegisterState.Idle,
    ) {
        enum class RegisterState { Idle, Registering, LoggedIn }
    }
}

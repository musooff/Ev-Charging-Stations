package com.titicorp.evcs.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titicorp.evcs.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private var loggingJob: Job? = null

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

    fun login() {
        _uiState.update {
            it.copy(
                loginState = UiState.LoginState.LoggingIn,
            )
        }
        loggingJob?.cancel()
        loggingJob = viewModelScope.launch {
            val user = loginUseCase(
                phoneNumber = uiState.value.phoneNumber,
                password = uiState.value.password,
            )
            _uiState.update {
                it.copy(
                    loginState = UiState.LoginState.LoggedIn,
                )
            }
        }
    }

    fun cancelLogin() {
        loggingJob?.cancel()
        loggingJob = null
        _uiState.update {
            it.copy(
                loginState = UiState.LoginState.Idle,
            )
        }
    }

    data class UiState(
        val phoneNumber: String = "",
        val password: String = "",
        val loginState: LoginState = LoginState.Idle,
    ) {
        enum class LoginState { Idle, LoggingIn, LoggedIn }
    }
}

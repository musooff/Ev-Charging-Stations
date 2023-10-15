package com.titicorp.evcs.ui.station.booking

import androidx.lifecycle.ViewModel
import com.titicorp.evcs.model.Charger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StationBookingViewModel @Inject constructor() : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        _uiState.value = UiState.Success(
            chargers = Charger.All,
            times = listOf(),
        )
    }

    sealed interface UiState {

        object Loading : UiState

        data class Success(
            val chargers: List<Charger>,
            val times: List<Int>,
        ) : UiState
    }
}

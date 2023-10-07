package com.titicorp.evcs.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titicorp.evcs.domain.GetNearbyStationsUseCase
import com.titicorp.evcs.model.Station
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    nearbyStationsUseCase: GetNearbyStationsUseCase,
) : ViewModel() {

    val uiState: StateFlow<UiState> = nearbyStationsUseCase()
        .map { UiState.Stations(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading,
        )

    sealed interface UiState {
        object Loading : UiState
        data class Stations(
            val data: List<Station>,
        ) : UiState
    }
}

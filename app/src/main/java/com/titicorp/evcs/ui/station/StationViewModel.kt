package com.titicorp.evcs.ui.station

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titicorp.evcs.domain.GetStationDetailsUseCase
import com.titicorp.evcs.model.StationDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getStationDetailsUseCase: GetStationDetailsUseCase,
) : ViewModel() {

    val uiState: StateFlow<UiState> = getStationDetailsUseCase(
        id = requireNotNull(savedStateHandle["stationId"]),
    )
        .map { UiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading,
        )

    sealed interface UiState {
        object Loading : UiState
        data class Success(
            val data: StationDetails,
        ) : UiState
    }
}

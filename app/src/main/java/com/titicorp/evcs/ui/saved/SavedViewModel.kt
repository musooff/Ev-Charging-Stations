package com.titicorp.evcs.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titicorp.evcs.domain.GetSavedStationsUseCase
import com.titicorp.evcs.model.Station
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    getSavedStationsUseCase: GetSavedStationsUseCase,
) : ViewModel() {

    private val _stations: MutableStateFlow<List<Station>> = MutableStateFlow(emptyList())
    val stations: StateFlow<List<Station>> = _stations

    init {
        viewModelScope.launch {
            _stations.value = getSavedStationsUseCase()
        }
    }
}

package com.titicorp.evcs.ui.mybookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titicorp.evcs.domain.GetMyBookingsUseCase
import com.titicorp.evcs.model.Booking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBookingsViewModel @Inject constructor(
    getMyBookingsUseCase: GetMyBookingsUseCase,
) : ViewModel() {

    private val _bookings: MutableStateFlow<List<Booking>> = MutableStateFlow(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings

    init {
        viewModelScope.launch {
            _bookings.value = getMyBookingsUseCase()
        }
    }
}

package com.example.ncl.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ncl.data.remote.models.CruiseShipResponse
import com.example.ncl.data.repository.ICruiseShipRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CruiseShipViewModel @Inject constructor(
    private val cruiseShipRepository: ICruiseShipRepository
) : ViewModel() {

    private var _state = MutableLiveData<State>(State.Init)
    val state: LiveData<State> get() = _state

    fun getCruiseShips(cruiseShip: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            cruiseShipRepository.getCruiseShips(cruiseShip)
                .onSuccess { response ->
                    _state.value = State.Success(response)
                }.onFailure {
                    _state.value = State.Failure
                }
        }
    }
}

sealed class State {
    object Init : State()
    object Loading : State()
    data class Success(val data: CruiseShipResponse) : State()
    object Failure : State()
}
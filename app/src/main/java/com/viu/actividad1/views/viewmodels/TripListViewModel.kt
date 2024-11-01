package com.viu.actividad1.views.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viu.actividad1.data.repository.TripRepository
import com.viu.actividad1.domain.model.Trip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// View model para la lista de viajes
class TripListViewModel(val repository: TripRepository) : ViewModel() {
    private val _trips: MutableState<List<Trip>> = mutableStateOf(emptyList());
    var tripsVM: State<List<Trip>> = _trips;

    var job: Job? = null;

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _trips.value = loadTrips(false);
            }

        }
    }

    // Cargar los viajes
    private suspend fun loadTrips(pastEvents: Boolean): List<Trip> {
        job?.cancel()

        job = repository.getAllTrips().onEach { trip ->
            _trips.value = trip.map {
                Trip.fromEntity(it);
            }

        }.launchIn(viewModelScope)

        return getTrips(pastEvents);
    }

    // Obtener los viajes en función de si están completados o no
    fun getTrips(completed: Boolean): List<Trip> {
        return _trips.value.filter { trip ->
            trip.completed == completed
        }
    }

}

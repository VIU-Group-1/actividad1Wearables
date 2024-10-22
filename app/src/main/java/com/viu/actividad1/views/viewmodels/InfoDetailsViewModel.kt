package com.viu.actividad1.views.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viu.actividad1.data.repository.TripRepository
import com.viu.actividad1.domain.model.Trip
import com.viu.actividad1.domain.model.UpdateStatus
import kotlinx.coroutines.launch

//View model para la pantalla de detalles de cada viaje
class InfoDetailsViewModel(private val repository: TripRepository) : ViewModel() {
    private val _updateStatus = MutableLiveData<UpdateStatus>()
    val updateStatus: LiveData<UpdateStatus> get() = _updateStatus

    private val _showDeleteDialog: MutableState<Boolean> = mutableStateOf(false)
    val showDeleteDialog: MutableState<Boolean> get() = _showDeleteDialog

    // Mostrar el cuadro de diálogo de confirmación de eliminación
    fun showDeleteDialog() {
        _showDeleteDialog.value = true
    }

    // Ocultar el cuadro de diálogo de confirmación de eliminación
    fun hideDeleteDialog() {
        _showDeleteDialog.value = false
    }

    //Elimina el viaje por id
    fun deleteTripById(tripId: Int) {
        viewModelScope.launch {
            val trip = repository.getTripById(tripId)
            if (trip != null) {
                repository.deleteTrip(trip);
            }
        }
    }

    // Obtener el viaje por id
    suspend fun getTripById(tripId: Int): Trip? {
        val trip = repository.getTripById(tripId)
        if (trip != null) {
            return Trip.fromEntity(trip)
        }
        return null;
    }


}

package com.viu.actividad1.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viu.actividad1.data.repository.TripRepository
import com.viu.actividad1.domain.TripEntity
import com.viu.actividad1.domain.model.Trip
import kotlinx.coroutines.launch

class InfoDetailsViewModel(val repository: TripRepository): ViewModel() {

    suspend fun getTripById(tripId: Int): Trip? {
        val trip = repository.getTripById(tripId)
        if(trip != null) {
            return Trip.fromEntity(trip)
        }
        return null;
    }

}

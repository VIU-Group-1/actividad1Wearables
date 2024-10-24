package com.viu.actividad1.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viu.actividad1.data.repository.TripRepository
import com.viu.actividad1.domain.TripEntity
import com.viu.actividad1.domain.model.Trip
import com.viu.actividad1.domain.model.UpdateStatus
import kotlinx.coroutines.launch

// View model para la valoración de viajes
class RateTripViewModel(val repository: TripRepository) : ViewModel() {
    private val _updatedStatus = MutableLiveData<UpdateStatus>()
    val updateStatus: LiveData<UpdateStatus> get() = _updatedStatus

    // Función de valoración de viaje
    fun rateTrip(
        trip: Trip?,
        punctuation: Int?,
        review: String?
    ) {

        viewModelScope.launch {
            try {
                if (trip!=null)
                {
                    val updatedTrip = TripEntity(
                        id = trip.id,
                        title = trip.title,
                        city = trip.city,
                        country = trip.country,
                        departureDate = TripEntity.convertDateToLong(trip.departureDate),
                        returnDate = TripEntity.convertDateToLong(trip.returnDate),
                        description = trip.description,
                        photoUrl = trip.photoUrl,
                        cost = trip.cost,
                        completed = true,
                        punctuation = punctuation,
                        review = review
                    )
                    repository.updateTrip(updatedTrip)
                    _updatedStatus.value = UpdateStatus.Updated("Viaje completado y valorado correctamente")
                }

            } catch (e: Exception) {
                _updatedStatus.value = UpdateStatus.Error("Eror al completar y valorar el viaje el viaje: ${e.message}")
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
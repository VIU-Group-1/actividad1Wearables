package com.viu.actividad1.views.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viu.actividad1.data.repository.TripRepository
import com.viu.actividad1.domain.TripEntity
import com.viu.actividad1.domain.model.InsertStatus
import kotlinx.coroutines.launch

// View model para la creacion de viajes
class NewTripViewModel(val repository: TripRepository) : ViewModel() {
    private val _insertStatus = MutableLiveData<InsertStatus>()
    val insertStatus: LiveData<InsertStatus> get() = _insertStatus

    fun addTrip(
        title: String,
        city: String,
        country: String,
        departureDate: Long,
        returnDate: Long,
        description: String,
        photoUrl: String,
        cost: Double,
        completed: Boolean,
        punctuation: Int?
    ) {

        viewModelScope.launch {
            try {
                val newTrip = TripEntity(
                    title = title,
                    city = city,
                    country = country,
                    departureDate = departureDate,
                    returnDate = returnDate,
                    description = description,
                    photoUrl = photoUrl,
                    cost = cost,
                    completed = completed,
                    punctuation = punctuation
                )
                repository.insertTrip(newTrip)
                _insertStatus.value = InsertStatus.Inserted("Viaje agregado correctamente")
            } catch (e: Exception) {
                _insertStatus.value = InsertStatus.Error("Eror al agregar el viaje: ${e.message}")
            }
        }
    }

    suspend fun getTripById(id: Int): TripEntity? {
        return repository.getTripById(id)
    }

    fun updateTrip(
        id: Int,
        title: String,
        city: String,
        country: String,
        departureDate: Long,
        returnDate: Long,
        description: String,
        photoUrl: String,
        cost: Double,
        completed: Boolean,
        punctuation: Int?
    ) {
        viewModelScope.launch {
            try {
                val updatedTrip = TripEntity(
                    id = id,
                    title = title,
                    city = city,
                    country = country,
                    departureDate = departureDate,
                    returnDate = returnDate,
                    description = description,
                    photoUrl = photoUrl,
                    cost = cost,
                    completed = completed,
                    punctuation = punctuation
                )
                repository.updateTrip(updatedTrip)
                _insertStatus.value = InsertStatus.Inserted("Viaje insertado correctamente")
            } catch (e: Exception) {
                _insertStatus.value =
                    InsertStatus.Error("Error al actualizar el viaje: ${e.message}")
            }
        }
    }
}
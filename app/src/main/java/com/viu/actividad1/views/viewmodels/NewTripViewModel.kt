package com.viu.actividad1.views.viewmodels

import android.provider.ContactsContract.Intents.Insert
import androidx.compose.runtime.State
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viu.actividad1.data.repository.TripRepository
import com.viu.actividad1.domain.TripEntity
import com.viu.actividad1.domain.model.Trip
import kotlinx.coroutines.launch
import java.sql.Date
sealed class InsertStatus {
    object Success: InsertStatus()
    data class Error (val message: String) : InsertStatus()
}

class NewTripViewModel(val repository: TripRepository): ViewModel() {
    private val _insertStatus = MutableLiveData<InsertStatus>()
    val insertStatus: LiveData<InsertStatus> get() = _insertStatus

    fun AddTrip(title: String,
                city: String,
                country: String,
                departureDate: Long,
                returnDate: Long,
                description: String,
                photoUrl: String,
                cost: Double,
                completed: Boolean,
                punctuation: Int?)
    {

        viewModelScope.launch {
            try{
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
                _insertStatus.value = InsertStatus.Success
            } catch (e: Exception){
                _insertStatus.value = InsertStatus.Error("Eror al agregar el viaje: ${e.message}")
            }
        }
    }
    suspend fun getTripById (id: Int): TripEntity?{
        return repository.getTripById(id)
    }
    fun UpdateTrip(
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
    ){
        viewModelScope.launch{
            try{
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
                _insertStatus.value = InsertStatus.Success
            } catch (e: Exception){
                _insertStatus.value = InsertStatus.Error("Error al actualizar el viaje: ${e.message}")
            }
        }
    }
}
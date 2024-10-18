package com.viu.actividad1.views.viewmodels

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viu.actividad1.data.repository.TripRepository
import com.viu.actividad1.domain.TripEntity
import com.viu.actividad1.domain.model.Trip
import kotlinx.coroutines.launch
import java.sql.Date

class NewTripViewModel(val repository: TripRepository): ViewModel() {
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
            repository.insertTrip(
                TripEntity(
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
            )
        }
    }
}
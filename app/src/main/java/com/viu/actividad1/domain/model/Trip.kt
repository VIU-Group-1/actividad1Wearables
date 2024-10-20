package com.viu.actividad1.domain.model;

import com.viu.actividad1.domain.TripEntity
import java.util.Date;

data class Trip(
    val id: Int = 0,
    val title: String,
    val city: String,
    val country: String,
    val departureDate: Date,
    val returnDate: Date,
    val description: String,
    val photoUrl: String,
    val cost: Double,
    val completed: Boolean,
    val punctuation: Int?
){
    companion object {
        fun fromEntity(entity: TripEntity): Trip{
            return Trip(
                id = entity.id,
                title = entity.title,
                departureDate = entity.getDepartureDateAsDate(),
                returnDate = entity.getReturnDateAsDate(),
                description = entity.description,
                city = entity.city,
                country = entity.country,
                photoUrl = entity.photoUrl,
                cost = entity.cost,
                completed = entity.completed,
                punctuation = entity.punctuation
            )
        }
    }
}

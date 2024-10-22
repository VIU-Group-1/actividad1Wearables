package com.viu.actividad1.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

// Entidad para almacenar mediante la libreria Room los viajes en BD
@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val city: String,
    val country: String,
    val departureDate: Long,
    val returnDate: Long,
    val description: String,
    val photoUrl: String,
    val cost: Double,
    var completed: Boolean,
    val punctuation: Int?
) {
    // Convertir Long a Date
    fun getDepartureDateAsDate(): Date {
        return Date(departureDate)
    }

    // Convertir Long a Date
    fun getReturnDateAsDate(): Date {
        return Date(returnDate)
    }

    companion object {
        // Convertir Date a Long
        fun convertDateToLong(date: Date): Long {
            return date.time
        }
    }
}
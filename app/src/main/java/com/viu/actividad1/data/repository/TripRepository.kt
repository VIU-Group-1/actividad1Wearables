package com.viu.actividad1.data.repository

import com.viu.actividad1.data.DAO.TripDao
import com.viu.actividad1.domain.TripEntity
import kotlinx.coroutines.flow.Flow

// Clase repositorio referente al patrón repositorio
class TripRepository(private val tripDao: TripDao) {

    //Insertar un viaje
    suspend fun insertTrip(tripEntity:TripEntity){
        tripDao.insertTrip(tripEntity)
    }
    // Obtener todos los viajes
    suspend fun getAllTrips(): Flow<List<TripEntity>> {
        return tripDao.getAllTrips()
    }

    // Obtener un viaje por su ID
    suspend fun getTripById(tripId: Int): TripEntity? {
        return tripDao.getTripById(tripId)
    }

    // Actualizar un viaje existente
    suspend fun updateTrip(tripEntity: TripEntity) {
        tripDao.updateTrip(tripEntity)
    }

    // Eliminar un viaje
    suspend fun deleteTrip(tripEntity: TripEntity) {
        tripDao.deleteTrip(tripEntity)
    }

    // Eliminar todos los viajes
    suspend fun deleteAllTrips() {
        tripDao.deleteAllTrips()
    }
}
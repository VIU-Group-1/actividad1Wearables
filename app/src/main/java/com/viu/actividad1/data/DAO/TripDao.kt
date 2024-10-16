package com.viu.actividad1.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.viu.actividad1.domain.TripEntity
import kotlinx.coroutines.flow.Flow

//Clase DAO para los viajes que permite seguir el patron
@Dao
interface TripDao {

    // Insertar un nuevo viaje
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(tripEntity: TripEntity)

    // Obtener todos los viajes
    @Query("SELECT * FROM trips")
    fun getAllTrips(): Flow<List<TripEntity>>

    // Obtener un viaje por su ID
    @Query("SELECT * FROM trips WHERE id = :tripId")
    suspend fun getTripById(tripId: Int): TripEntity?

    // Actualizar un viaje
    @Update
    suspend fun updateTrip(tripEntity: TripEntity)

    // Eliminar un viaje
    @Delete
    suspend fun deleteTrip(tripEntity: TripEntity)

    // Eliminar todos los viajes
    @Query("DELETE FROM trips")
    suspend fun deleteAllTrips()
}

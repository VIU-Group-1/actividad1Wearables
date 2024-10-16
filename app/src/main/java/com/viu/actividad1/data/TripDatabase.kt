package com.viu.actividad1.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.viu.actividad1.data.DAO.TripDao
import com.viu.actividad1.domain.TripEntity

//Clase para la creación de base de datos de la biblioteca Room
@Database(entities = [TripEntity::class], version = 1)
abstract class TripDatabase: RoomDatabase() {
    abstract val dao: TripDao;

    companion object {
        const val DATABASE_NAME = "trips.db"
    }
}
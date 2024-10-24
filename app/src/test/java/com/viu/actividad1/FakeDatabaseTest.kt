package com.viu.actividad1

import com.viu.actividad1.data.DAO.TripDao
import com.viu.actividad1.domain.TripEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale

class FakeDatabase : TripDao {

    // Lista que simula los datos de la base de datos
    private val trips = mutableListOf<TripEntity>()

    // Obtener todos los viajes
    override fun getAllTrips(): Flow<List<TripEntity>> = flow {
        emit(trips)
    }

    // Obtener un viaje por su ID
    override suspend fun getTripById(tripId: Int): TripEntity? {
        return trips.find { it.id == tripId }
    }

    // Insertar un nuevo viaje
    override suspend fun insertTrip(tripEntity: TripEntity) {
        trips.removeIf { it.id == tripEntity.id }
        trips.add(tripEntity)
    }

    // Actualizar un viaje existente
    override suspend fun updateTrip(trip: TripEntity) {
        val index = trips.indexOfFirst { it.id == trip.id }
        if (index != -1) {
            trips[index] = trip
        }
    }

    // Eliminar un viaje
    override suspend fun deleteTrip(tripEntity: TripEntity) {
        trips.remove(tripEntity)
    }

    // Eliminar todos los viajes
    override suspend fun deleteAllTrips() {
        trips.clear()
    }
}
class FakeDatabaseTest {

    private lateinit var fakeDatabase: FakeDatabase

    @Before
    fun setUp() {
        fakeDatabase = FakeDatabase()
    }

    // Añadimos un viaje falso
    private val trip = TripEntity(
        id = 1,
        title = "Viaje a París",
        city = "París",
        country = "Francia",
        departureDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2024-10-23")?.time ?: 0L,
        returnDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2024-10-31")?.time ?: 0L,
        description = "Viaje de trabajo",
        photoUrl = "https://img.freepik.com/vector-gratis/conjunto-pegatinas-distintivos-viaje_53876-100734.jpg?t=st=1729796259~exp=1729799859~hmac=6ef4755faed42037b081fb214b6b3e97c311668be957f27c336f92fc4e0f409b&w=996",
        cost = 1000.00,
        completed = true,
        punctuation = 0,
        review = ""
    )

    // Testear insertar viaje falso
    @Test
    fun testInsertTrip() = runBlocking {
        // Insertamos el viaje falso
        fakeDatabase.insertTrip(trip)

        // Obtenemos todos los viajes y comprobamos que está Viaje a París
        val trips = fakeDatabase.getAllTrips().first()
        assertEquals(1, trips.size)
        assertEquals("Viaje a París", trips[0].title)
    }

    // Testear eliminar viaje falso
    @Test
    fun testDeleteTrip() = runBlocking {
        // Insertamos el viaje primero
        fakeDatabase.insertTrip(trip)

        // Ahora eliminamos el viaje
        fakeDatabase.deleteTrip(trip)

        // Obtenemos todos los viajes y comprobamos que no hay viajes
        val trips = fakeDatabase.getAllTrips().first()
        // Comprobar que la lista de viajes está vacía
        assertEquals(0, trips.size)
    }

    // Testear eliminar todos los viajes
    @Test
    fun testDeleteAllTrips() = runBlocking {
        // Insertamos el viaje primero
        fakeDatabase.insertTrip(trip)

        // Eliminamos todos los viajes
        fakeDatabase.deleteAllTrips()

        // Obtenemos todos los viajes y comprobamos que no hay viajes
        val trips = fakeDatabase.getAllTrips().first()
        // Comprobar que la lista de viajes está vacía
        assertEquals(0, trips.size)
    }

    // Testear actualizar viaje
    @Test
    fun testUpdateTrip() = runBlocking {
        // Insertamos el viaje
        fakeDatabase.insertTrip(trip)

        // Actualizamos el viaje
        val updatedTrip = trip.copy(title = "Viaje a Londres", cost = 1200.00)
        fakeDatabase.updateTrip(updatedTrip)

        // Obtenemos todos los viajes y comprobamos que el viaje a Londres se ha actualizado
        val trips = fakeDatabase.getAllTrips().first()
        assertEquals(1, trips.size)
        assertEquals("Viaje a Londres", trips[0].title)
        // Comprobar que el costo se ha actualizado
        assertEquals(1200.00, trips[0].cost, 0.01)
    }
}
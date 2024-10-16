package com.viu.actividad1.views.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel;
import com.viu.actividad1.domain.TripEntity
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.viu.actividad1.data.DAO.TripDao
import com.viu.actividad1.domain.model.Trip
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.sql.Date

// View model para la lista de viajes
class TripListViewModel(val dao: TripDao): ViewModel() {
    private val _trips: MutableState<List<Trip>> = mutableStateOf(emptyList());
    var tripsVM: State<List<Trip>> = _trips;

    var job: Job? = null;
    init{
        viewModelScope.launch {
            dao.deleteAllTrips();
            dao.insertTrip(
                TripEntity(
                    title = "Escapada a la Ciudad",
                    city = "Tokio",
                    country = "Japón",
                    departureDate = TripEntity.convertDateToLong(Date(2024 - 1900, 10 - 1, 10)),
                    returnDate = TripEntity.convertDateToLong(Date(2024 - 1900, 10 - 1, 20)),
                    description = "Una semana explorando la vibrante ciudad de Tokio.",
                    photoUrl = "https://elhype.com/wp-content/uploads/2020/01/tokyo-lifestyle-arquitectura-elhype-c-690x450.jpg",
                    cost = 1800.00,
                    completed = false
                )
            )
            dao.insertTrip(
                TripEntity(
                    title = "Escapada a la Ciudad",
                    city = "Tokio",
                    country = "Japón",
                    departureDate = TripEntity.convertDateToLong(Date(2024 - 1900, 10 - 1, 10)),
                    returnDate = TripEntity.convertDateToLong(Date(2024 - 1900, 10 - 1, 20)),
                    description = "Una semana explorando la vibrante ciudad de Tokio.",
                    photoUrl = "https://elhype.com/wp-content/uploads/2020/01/tokyo-lifestyle-arquitectura-elhype-c-690x450.jpg",
                    cost = 1800.00,
                    completed = false
                )
            )

            dao.insertTrip(
                TripEntity(
                    title = "Exploración Cultural",
                    city = "París",
                    country = "Francia",
                    departureDate = TripEntity.convertDateToLong(Date(2024 - 1900, 7 - 1, 15)),
                    returnDate = TripEntity.convertDateToLong(Date(2024 - 1900, 7 - 1, 25)),
                    description = "Explorando el arte y la cultura de París.",
                    photoUrl = "https://media-cdn.tripadvisor.com/media/photo-c/1280x250/17/15/6d/d6/paris.jpg",
                    cost = 2500.00,
                    completed = false
                )
            )

            dao.insertTrip(
                TripEntity(
                    title = "Aventura en las Montañas",
                    city = "Aspen",
                    country = "EE.UU.",
                    departureDate = TripEntity.convertDateToLong(Date(2024 - 1900, 8 - 1, 10)),
                    returnDate = TripEntity.convertDateToLong(Date(2024 - 1900, 8 - 1, 17)),
                    description = "Senderismo y esquí en las hermosas montañas.",
                    photoUrl = "https://mediaim.expedia.com/destination/1/7654d3634dae6a17e5fc54fb7aaab3f0.jpg",
                    cost = 2000.00,
                    completed = false
                )
            )

            dao.insertTrip(
                TripEntity(
                    title = "Aventura de Safari",
                    city = "Nairobi",
                    country = "Kenia",
                    departureDate = TripEntity.convertDateToLong(Date(2024 - 1900, 9 - 1, 1)),
                    returnDate = TripEntity.convertDateToLong(Date(2024 - 1900, 9 - 1, 15)),
                    description = "Experimenta la vida salvaje como nunca antes.",
                    photoUrl = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/29/f3/b4/19/caption.jpg?w=500&h=400&s=1",
                    cost = 3000.00,
                    completed = false
                )
            )
            _trips.value = loadTrips(false);

        }
    }

    private suspend fun loadTrips(pastEvents: Boolean): List<Trip>{
        job?.cancel()

        job = dao.getAllTrips().onEach { trip ->
            _trips.value = trip.map {
                Trip.fromEntity(it);
            }

        }.launchIn(viewModelScope)

        return getTrips(pastEvents);
    }

    fun getTrips(pastEvents: Boolean): List<Trip> {
        return _trips.value.filter { trip ->
             trip.completed == pastEvents
        }
    }

}

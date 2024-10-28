package com.viu.actividad1.views.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viu.actividad1.data.api.RetrofitInstance
import com.viu.actividad1.data.repository.TripRepository
import com.viu.actividad1.domain.TripEntity
import com.viu.actividad1.domain.model.Country
import com.viu.actividad1.domain.model.InsertStatus
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// View model para la creacion de viajes
class NewTripViewModel(val repository: TripRepository) : ViewModel() {
    private val _insertStatus = MutableLiveData<InsertStatus>()
    val insertStatus: LiveData<InsertStatus> get() = _insertStatus
    val countriesLiveData = MutableLiveData<List<Country>>()
    val errorLiveData = MutableLiveData<String>()


    // Añadir un viaje
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
        punctuation: Int?,
        review: String?
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
                    punctuation = punctuation,
                    review = review
                )
                repository.insertTrip(newTrip)
                _insertStatus.value = InsertStatus.Inserted("Viaje agregado correctamente")
            } catch (e: Exception) {
                _insertStatus.value = InsertStatus.Error("Eror al agregar el viaje: ${e.message}")
            }
        }
    }

    // Obtener un viaje por ID
    suspend fun getTripById(id: Int): TripEntity? {
        return repository.getTripById(id)
    }

    // Actualizar un viaje
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
        punctuation: Int?,
        review: String?
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
                    punctuation = punctuation,
                    review = review
                )
                repository.updateTrip(updatedTrip)
                _insertStatus.value = InsertStatus.Inserted("Viaje insertado correctamente")
            } catch (e: Exception) {
                _insertStatus.value =
                    InsertStatus.Error("Error al actualizar el viaje: ${e.message}")
            }
        }
    }

    // Obtener paises de la API
    fun fetchCountries() {
        viewModelScope.launch {
            val call = RetrofitInstance.api.getCountries()
            call.enqueue(object : Callback<List<Country>> {
                override fun onResponse(
                    call: Call<List<Country>>,
                    response: Response<List<Country>>
                ) {
                    if (response.isSuccessful) {
                        countriesLiveData.postValue(response.body())
                    } else {
                        errorLiveData.postValue("Error en la respuesta: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                    errorLiveData.postValue("Error en la llamada a la API: ${t.message}")
                }
            })
        }
    }

    // Comprobar si el pais introducido es válido segun la API
    fun isCountryValid(country: String): Boolean {
        return countriesLiveData.value?.any {
            it.translations?.get("spa")?.common.equals(country, ignoreCase = true)
        } == true
    }
}
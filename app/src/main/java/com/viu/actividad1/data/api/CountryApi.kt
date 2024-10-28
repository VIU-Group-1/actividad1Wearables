package com.viu.actividad1.data.api


import com.viu.actividad1.domain.model.Country
import retrofit2.Call
import retrofit2.http.GET

// Api con retrofit para obtener los paises
interface CountryApi {
    @GET("all")
    fun getCountries(): Call<List<Country>>
}
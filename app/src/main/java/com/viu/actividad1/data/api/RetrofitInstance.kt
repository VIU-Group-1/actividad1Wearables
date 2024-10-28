package com.viu.actividad1.data.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Instancia de la API con retrofit
object RetrofitInstance {
    private const val BASE_URL = "https://restcountries.com/v3.1/"

    val api: CountryApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryApi::class.java)
    }
}

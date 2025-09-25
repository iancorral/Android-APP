package com.jorgeromo.androidClassMp1.secondpartial.home.network

import com.jorgeromo.androidClassMp1.secondpartial.home.model.HomeScreenResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// URL base de GitHub Gist
private const val BASE_URL = "https://gist.githubusercontent.com"

// Interface que define los endpoints
interface ApiService {
    // Usamos @GET con la ruta específica de TU Gist
    @GET("/iancorral/8068b8b9513c0f06e7a745c63cfd2787/raw/74b39a0cd366c24b127da723e80fb40eadc00127/HomeData.json")
    suspend fun getHomeScreenData(): HomeScreenResponse
}

// Objeto singleton para crear y proveer la instancia de Retrofit
object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
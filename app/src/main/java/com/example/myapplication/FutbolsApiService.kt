package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
private const val BASE_URL = "https://api.myjson.com/"

interface FutbolsApiService {
    @GET("{url}")
    suspend fun getProperties(@Path(value = "url", encoded = false)url: String): JsonDTO
}

val retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(FutbolsApiService::class.java)
}
//https://api.myjson.com/bins/qqvuq
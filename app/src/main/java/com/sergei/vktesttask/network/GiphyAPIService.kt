package com.sergei.vktesttask.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.giphy.com/v1/gifs/"

// Это конечно не стоит хранить в публичном репозитории, но оставляю так для удобства проверки :)
private const val API_KEY = "ARGGt37zAdECsh7l9yZL9eQwS1DR5KSP"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * Uses Retrofit and Moshi to
 * get response from Giphy.
 * Для получения ответа от Giphy
 * использую Retrofit для запроса к АПИ
 * и Moshi для преобразования JSON
 * в дата класс.
 * Используется синглтон.
 */
interface GiphyAPIService {
    @GET("search?api_key=${API_KEY}")
    suspend fun getGiphySearchResponse(
        @Query("q") searchQuery: String,
        @Query("limit") limitRecords: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String,
        @Query("lang") language: String
    ): GiphyResponse
}

object GiphyAPI {
    val retrofitAPIService : GiphyAPIService by lazy {
        retrofit.create(GiphyAPIService::class.java)
    }
}
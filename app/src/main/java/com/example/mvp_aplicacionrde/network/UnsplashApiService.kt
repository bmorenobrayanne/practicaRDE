package com.example.mvp_aplicacionrde.network

import retrofit2.http.GET
import retrofit2.http.Query

data class UnsplashResponse(
    val urls: Urls
)

data class Urls(
    val regular: String
)

interface UnsplashApiService {
    @GET("photos/random")
    suspend fun getRandomImage(
        @Query("client_id") clientId: String = "5sW-OeT6to7ge2_hCxNHWPrlud6VORGtEbxsyJlR-jY",
        @Query("query") query: String = "natural disaster"
    ): UnsplashResponse
}
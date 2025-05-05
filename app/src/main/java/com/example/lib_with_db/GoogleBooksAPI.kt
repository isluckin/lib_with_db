package com.example.lib_with_db


import retrofit2.http.GET
import retrofit2.http.Query

internal interface GoogleBooksAPI {
    @GET("books/v1/volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20,
        @Query("key") apiKey: String
    ): GoogleBooksResponse
}

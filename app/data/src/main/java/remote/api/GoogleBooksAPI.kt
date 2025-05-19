package remote.api

import remote.response.GoogleBooksResponse
import retrofit2.http.GET
import retrofit2.http.Query


 interface GoogleBooksAPI {
    @GET("books/v1/volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20,
        @Query("key") apiKey: String
    ): GoogleBooksResponse
}

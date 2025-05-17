package com.example.lib_with_db


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

internal object RetrofitHelper {
    private fun createConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true

        }
        return json.asConverterFactory(contentType)
    }

    fun createRetrofit(): GoogleBooksAPI {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val okHttpClient = OkHttpClient().newBuilder().addInterceptor((loggingInterceptor)).build()
        val retrofit = Retrofit.Builder().baseUrl("https://www.googleapis.com/")
            .addConverterFactory(createConverterFactory()).client(okHttpClient).build()
        return retrofit.create(GoogleBooksAPI::class.java)
    }
}
package com.example.di

import dagger.Provides
import kotlin.apply
import kotlin.jvm.java

@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): okhttp3.logging.HttpLoggingInterceptor {
        return okhttp3.logging.HttpLoggingInterceptor().apply {
            level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: okhttp3.logging.HttpLoggingInterceptor): okhttp3.OkHttpClient {
        return okhttp3.OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: okhttp3.OkHttpClient): retrofit2.Retrofit {
        return retrofit2.Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .client(client)
            .addConverterFactory(kotlinx.serialization.json.Json.Default.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    fun provideGoogleBooksApi(retrofit: retrofit2.Retrofit): GoogleBooksAPI {
        return retrofit.create(GoogleBooksAPI::class.java)
    }
}
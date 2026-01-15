package com.example.aplikasipamproject.repositori

import com.example.aplikasipamproject.apiservice.KuanApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

// Interface Kontrak Utama
interface AppContainer {
    val kuanRepository: KuanRepository
    val userPreferencesRepository: UserPreferencesRepository
}

class KuanContainer(private val context: android.content.Context) : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Membuat OkHttpClient dan menambahkan interceptor
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(httpClient) // Menggunakan http client kustom
        .build()

    private val kuanApiService: KuanApiService by lazy {
        retrofit.create(KuanApiService::class.java)
    }

    override val kuanRepository: KuanRepository by lazy {
        JaringanKuanRepository(kuanApiService)
    }

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.dataStore)
    }
}
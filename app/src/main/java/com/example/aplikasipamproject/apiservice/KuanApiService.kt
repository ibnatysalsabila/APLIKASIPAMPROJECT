package com.example.aplikasipamproject.apiservice

import com.example.aplikasipamproject.modeldata.*
import retrofit2.Response
import retrofit2.http.*

interface KuanApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("pemilik") suspend fun getAllPemilik(@Header("Authorization") token: String): List<Pemilik>
    @POST("pemilik/create") suspend fun createPemilik(@Header("Authorization") token: String, @Body data: Pemilik): Response<Unit>
    @PUT("pemilik/update/{id}") suspend fun updatePemilik(@Header("Authorization") token: String, @Path("id") id: Int, @Body data: Pemilik): Response<Unit>
    @DELETE("pemilik/delete/{id}") suspend fun deletePemilik(@Header("Authorization") token: String, @Path("id") id: Int): Response<Unit>

    @GET("hewan") suspend fun getAllHewan(@Header("Authorization") token: String): List<Hewan>
    @POST("hewan/create") suspend fun createHewan(@Header("Authorization") token: String, @Body data: Hewan): Response<Unit>
    @PUT("hewan/update/{id}") suspend fun updateHewan(@Header("Authorization") token: String, @Path("id") id: Int, @Body data: Hewan): Response<Unit>
    @DELETE("hewan/delete/{id}") suspend fun deleteHewan(@Header("Authorization") token: String, @Path("id") id: Int): Response<Unit>

    @GET("layanan") suspend fun getAllLayanan(@Header("Authorization") token: String): List<LayananMedis>
    @POST("layanan/create") suspend fun createLayanan(@Header("Authorization") token: String, @Body data: LayananMedis): Response<Unit>
    @PUT("layanan/update/{id}") suspend fun updateLayanan(@Header("Authorization") token: String, @Path("id") id: Int, @Body data: LayananMedis): Response<Unit>
    @DELETE("layanan/delete/{id}") suspend fun deleteLayanan(@Header("Authorization") token: String, @Path("id") id: Int): Response<Unit>

    @GET("pemeriksaan") suspend fun getAllPemeriksaan(@Header("Authorization") token: String): List<Pemeriksaan>
    @POST("pemeriksaan/create") suspend fun createPemeriksaan(@Header("Authorization") token: String, @Body data: Pemeriksaan): Response<Unit>
    @PUT("pemeriksaan/update/{id}") suspend fun updatePemeriksaan(@Header("Authorization") token: String, @Path("id") id: Int, @Body data: Pemeriksaan): Response<Unit>
    @DELETE("pemeriksaan/delete/{id}") suspend fun deletePemeriksaan(@Header("Authorization") token: String, @Path("id") id: Int): Response<Unit>
}
package com.example.aplikasipamproject.repositori

import com.example.aplikasipamproject.apiservice.KuanApiService
import com.example.aplikasipamproject.modeldata.*

interface KuanRepository {
    suspend fun login(req: LoginRequest): LoginResponse

    // Pemilik
    suspend fun getPemilik(token: String): List<Pemilik>
    suspend fun insertPemilik(token: String, d: Pemilik)
    suspend fun updatePemilik(token: String, id: Int, d: Pemilik)
    suspend fun deletePemilik(token: String, id: Int)

    // Hewan
    suspend fun getHewan(token: String): List<Hewan>
    suspend fun insertHewan(token: String, d: Hewan)
    suspend fun updateHewan(token: String, id: Int, d: Hewan)
    suspend fun deleteHewan(token: String, id: Int)

    // Layanan Medis
    suspend fun getLayanan(token: String): List<LayananMedis>
    suspend fun insertLayanan(token: String, d: LayananMedis)
    suspend fun updateLayanan(token: String, id: Int, d: LayananMedis)
    suspend fun deleteLayanan(token: String, id: Int)

    // Riwayat Pemeriksaan
    suspend fun getPemeriksaan(token: String): List<Pemeriksaan>
    suspend fun insertPemeriksaan(token: String, d: Pemeriksaan)
    suspend fun updatePemeriksaan(token: String, id: Int, d: Pemeriksaan)
    suspend fun deletePemeriksaan(token: String, id: Int)
}

class JaringanKuanRepository(private val apiService: KuanApiService) : KuanRepository {

    override suspend fun login(req: LoginRequest) = apiService.login(req)

    // Implementasi Pemilik
    override suspend fun getPemilik(token: String): List<Pemilik> = apiService.getAllPemilik("Bearer $token")

    override suspend fun insertPemilik(token: String, d: Pemilik) {
        val response = apiService.createPemilik("Bearer $token", d)
        if (!response.isSuccessful) throw Exception("Gagal simpan pemilik: ${response.code()}")
    }

    override suspend fun updatePemilik(token: String, id: Int, d: Pemilik) {
        val response = apiService.updatePemilik("Bearer $token", id, d)
        if (!response.isSuccessful) throw Exception("Gagal update pemilik")
    }

    override suspend fun deletePemilik(token: String, id: Int) {
        val response = apiService.deletePemilik("Bearer $token", id)
        if (!response.isSuccessful) throw Exception("Gagal hapus pemilik")
    }

    // Implementasi Hewan
    override suspend fun getHewan(token: String): List<Hewan> = apiService.getAllHewan("Bearer $token")

    override suspend fun insertHewan(token: String, d: Hewan) {
        val response = apiService.createHewan("Bearer $token", d)
        if (!response.isSuccessful) throw Exception("Gagal simpan hewan")
    }

    override suspend fun updateHewan(token: String, id: Int, d: Hewan) {
        val response = apiService.updateHewan("Bearer $token", id, d)
        if (!response.isSuccessful) throw Exception("Gagal update hewan")
    }

    override suspend fun deleteHewan(token: String, id: Int) {
        val response = apiService.deleteHewan("Bearer $token", id)
        if (!response.isSuccessful) throw Exception("Gagal hapus hewan")
    }

    // Implementasi Layanan Medis
    override suspend fun getLayanan(token: String): List<LayananMedis> = apiService.getAllLayanan("Bearer $token")

    override suspend fun insertLayanan(token: String, d: LayananMedis) {
        val response = apiService.createLayanan("Bearer $token", d)
        if (!response.isSuccessful) throw Exception("Gagal simpan layanan")
    }

    override suspend fun updateLayanan(token: String, id: Int, d: LayananMedis) {
        val response = apiService.updateLayanan("Bearer $token", id, d)
        if (!response.isSuccessful) throw Exception("Gagal update layanan")
    }

    override suspend fun deleteLayanan(token: String, id: Int) {
        val response = apiService.deleteLayanan("Bearer $token", id)
        if (!response.isSuccessful) throw Exception("Gagal hapus layanan")
    }

    // Implementasi Riwayat Pemeriksaan
    override suspend fun getPemeriksaan(token: String): List<Pemeriksaan> = apiService.getAllPemeriksaan("Bearer $token")

    override suspend fun insertPemeriksaan(token: String, d: Pemeriksaan) {
        val response = apiService.createPemeriksaan("Bearer $token", d)
        if (!response.isSuccessful) throw Exception("Gagal simpan pemeriksaan")
    }

    override suspend fun updatePemeriksaan(token: String, id: Int, d: Pemeriksaan) {
        val response = apiService.updatePemeriksaan("Bearer $token", id, d)
        if (!response.isSuccessful) throw Exception("Gagal update pemeriksaan")
    }

    override suspend fun deletePemeriksaan(token: String, id: Int) {
        val response = apiService.deletePemeriksaan("Bearer $token", id)
        if (!response.isSuccessful) throw Exception("Gagal hapus pemeriksaan")
    }
}
package com.example.aplikasipamproject.modeldata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val username: String, val password: String)
@Serializable
data class LoginResponse(val success: Boolean, val message: String, val token: String? = null)

@Serializable
data class Pemilik(
    @SerialName("IDPemilik") val id_pemilik: Int = 0,
    @SerialName("Nama") val nama_pemilik: String,
    @SerialName("NoHP") val no_hp: String,
    @SerialName("Email") val email: String
)
data class UIStatePemilik(val detailPemilik: DetailPemilik = DetailPemilik(), val isEntryValid: Boolean = false)
data class DetailPemilik(
    val id_pemilik: Int = 0,
    val nama_pemilik: String = "",
    val no_hp: String = "",
    val email: String = ""
)

@Serializable
data class Hewan(
    @SerialName("IDHewan") val id_hewan: Int = 0,
    @SerialName("NamaHewan") val nama_hewan: String,
    @SerialName("Jenis") val jenis_hewan: String,
    @SerialName("IDPemilik") val id_pemilik: Int,
    @SerialName("NamaPemilik") val nama_pemilik: String? = null
)
data class UIStateHewan(val detailHewan: DetailHewan = DetailHewan(), val isEntryValid: Boolean = false)
data class DetailHewan(
    val id_hewan: Int = 0,
    val nama_hewan: String = "",
    val jenis_hewan: String = "",
    val id_pemilik: Int = 0
)

@Serializable
data class LayananMedis(
    @SerialName("IDLayanan") val id_layanan: Int = 0,
    @SerialName("NamaLayanan") val nama_layanan: String,
    @SerialName("Deskripsi") val deskripsi: String,
    @SerialName("Biaya") val biaya: String
)
data class UIStateLayanan(val detailLayanan: DetailLayanan = DetailLayanan(), val isEntryValid: Boolean = false)
data class DetailLayanan(
    val id_layanan: Int = 0,
    val nama_layanan: String = "",
    val deskripsi: String = "",
    val biaya: String = ""
)

@Serializable
data class Pemeriksaan(
    @SerialName("IDRiwayat") val id_pemeriksaan: Int = 0,
    @SerialName("TanggalPemeriksaan") val tgl_pemeriksaan: String,
    @SerialName("CatatanPemeriksaan") val catatan_medis: String,
    @SerialName("IDHewan") val id_hewan: Int,
    @SerialName("IDLayanan") val id_layanan: Int,
    @SerialName("NamaHewan") val nama_hewan: String? = null,
    @SerialName("NamaLayanan") val nama_layanan: String? = null
)
data class UIStatePemeriksaan(val detailPemeriksaan: DetailPemeriksaan = DetailPemeriksaan(), val isEntryValid: Boolean = false)
data class DetailPemeriksaan(
    val id_pemeriksaan: Int = 0,
    val tgl_pemeriksaan: String = java.time.LocalDate.now().toString(),
    val catatan_medis: String = "",
    val id_hewan: Int = 0,
    val id_layanan: Int = 0
)

fun DetailPemilik.toPemilik(): Pemilik = Pemilik(id_pemilik, nama_pemilik, no_hp, email)
fun DetailHewan.toHewan(): Hewan = Hewan(id_hewan, nama_hewan, jenis_hewan, id_pemilik)
fun DetailLayanan.toLayanan(): LayananMedis = LayananMedis(id_layanan, nama_layanan, deskripsi, biaya)
fun DetailPemeriksaan.toPemeriksaan(): Pemeriksaan = Pemeriksaan(id_pemeriksaan, tgl_pemeriksaan, catatan_medis, id_hewan, id_layanan)

fun com.example.aplikasipamproject.viewmodel.pemilikhewan.PemilikEvent.toPemilik(): Pemilik = Pemilik(
    id_pemilik = id_pemilik,
    nama_pemilik = nama,
    no_hp = noHP,
    email = email
)
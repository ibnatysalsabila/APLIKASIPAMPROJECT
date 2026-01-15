package com.example.aplikasipamproject.viewmodel.riwayatpemeriksaan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.*
import com.example.aplikasipamproject.repositori.KuanRepository
import kotlinx.coroutines.launch
import java.io.IOException

class InsertPemeriksaanViewModel(private val kuanRepository: KuanRepository) : ViewModel() {

    var uiStatePemeriksaan by mutableStateOf(UIStatePemeriksaan())
        private set

    var animals by mutableStateOf<List<Hewan>>(emptyList())
        private set

    var services by mutableStateOf<List<LayananMedis>>(emptyList())
        private set

    var selectedAnimalId by mutableStateOf(0)
    var selectedServiceId by mutableStateOf(0)

    fun fetchAllData(token: String) {
        val useToken = if (token == "your_token_here" || token == "dummy_token") "dummy_token" else token
        viewModelScope.launch {
            try {
                animals = kuanRepository.getHewan(useToken)
                services = kuanRepository.getLayanan(useToken)
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    fun updateInsertPemeriksaanState(detailPemeriksaan: DetailPemeriksaan) {
        uiStatePemeriksaan = UIStatePemeriksaan(
            detailPemeriksaan = detailPemeriksaan,
            isEntryValid = validateInput(detailPemeriksaan)
        )
    }

    private fun validateInput(uiState: DetailPemeriksaan = uiStatePemeriksaan.detailPemeriksaan): Boolean {
        return with(uiState) {
            tgl_pemeriksaan.isNotBlank() && catatan_medis.isNotBlank() && id_hewan != 0 && id_layanan != 0
        }
    }

    suspend fun insertPemeriksaan(token: String) {
        if (validateInput()) {
            val useToken = if (token == "your_token_here" || token == "dummy_token") "dummy_token" else token
            kuanRepository.insertPemeriksaan(useToken, uiStatePemeriksaan.detailPemeriksaan.toPemeriksaan())
        }
    }
}

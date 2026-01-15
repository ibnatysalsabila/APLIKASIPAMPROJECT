package com.example.aplikasipamproject.viewmodel.riwayatpemeriksaan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.*
import com.example.aplikasipamproject.repositori.KuanRepository
import com.example.aplikasipamproject.view.formatDate
import kotlinx.coroutines.launch

class EditPemeriksaanViewModel(
    savedStateHandle: SavedStateHandle,
    private val kuanRepository: KuanRepository
) : ViewModel() {

    var uiStatePemeriksaan by mutableStateOf(UIStatePemeriksaan())
        private set

    var animals by mutableStateOf<List<Hewan>>(emptyList())
        private set

    var services by mutableStateOf<List<LayananMedis>>(emptyList())
        private set

    private val idPemeriksaan: Int = checkNotNull(savedStateHandle["idPemeriksaan"])

    var selectedAnimalId by mutableStateOf(0)
    var selectedServiceId by mutableStateOf(0)

    fun fetchAllData(token: String) {
        val useToken = if (token == "your_token_here" || token == "dummy_token") "dummy_token" else token
        viewModelScope.launch {
            try {
                animals = kuanRepository.getHewan(useToken)
                services = kuanRepository.getLayanan(useToken)
                getPemeriksaanById(useToken)
            } catch (e: Exception) {
            }
        }
    }

    private fun getPemeriksaanById(token: String) {
        viewModelScope.launch {
            try {
                val listPemeriksaan = kuanRepository.getPemeriksaan(token)
                val pemeriksaan = listPemeriksaan.find { it.id_pemeriksaan == idPemeriksaan }
                pemeriksaan?.let {
                    selectedAnimalId = it.id_hewan
                    selectedServiceId = it.id_layanan
                    uiStatePemeriksaan = UIStatePemeriksaan(
                        detailPemeriksaan = DetailPemeriksaan(
                            id_pemeriksaan = it.id_pemeriksaan,
                            tgl_pemeriksaan = formatDate(it.tgl_pemeriksaan),
                            catatan_medis = it.catatan_medis,
                            id_hewan = it.id_hewan,
                            id_layanan = it.id_layanan
                        ),
                        isEntryValid = true
                    )
                }
            } catch (e: Exception) {
            }
        }
    }

    fun updateUiState(detailPemeriksaan: DetailPemeriksaan) {
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

    suspend fun updatePemeriksaan(token: String) {
        if (validateInput()) {
            val useToken = if (token == "your_token_here" || token == "dummy_token") "dummy_token" else token
            kuanRepository.updatePemeriksaan(useToken, idPemeriksaan, uiStatePemeriksaan.detailPemeriksaan.toPemeriksaan())
        }
    }
}

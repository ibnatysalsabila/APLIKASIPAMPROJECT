package com.example.aplikasipamproject.viewmodel.pemilikhewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.Pemilik
import com.example.aplikasipamproject.repositori.KuanRepository
import kotlinx.coroutines.launch

// State diletakkan di luar kelas atau di dalam pendukung agar rapi
data class DetailUiState(
    val pemilik: Pemilik? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)

class DetailPemilikViewModel(private val kuanRepository: KuanRepository) : ViewModel() {

    var detailUiState by mutableStateOf(DetailUiState())
        private set

    fun getDetailPemilik(id: Int, token: String = "") {
        viewModelScope.launch {
            // Set loading di awal
            detailUiState = DetailUiState(isLoading = true)
            try {
                // Ambil semua data lalu cari yang ID-nya cocok
                val response = kuanRepository.getPemilik(token)
                val result = response.find { it.id_pemilik == id }

                if (result != null) {
                    // Jika ketemu
                    detailUiState = DetailUiState(pemilik = result, isLoading = false)
                } else {
                    // Jika ID tidak ada dalam list
                    detailUiState = DetailUiState(
                        isError = true,
                        isLoading = false,
                        errorMessage = "Data pemilik tidak ditemukan"
                    )
                }
            } catch (e: Exception) {
                // Jika terjadi error jaringan atau server
                detailUiState = DetailUiState(
                    isError = true,
                    isLoading = false,
                    errorMessage = e.message ?: "Terjadi kesalahan jaringan"
                )
            }
        }
    }
}
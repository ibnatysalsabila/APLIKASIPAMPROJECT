package com.example.aplikasipamproject.viewmodel.hewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.DetailHewan
import com.example.aplikasipamproject.modeldata.UIStateHewan
import com.example.aplikasipamproject.modeldata.toHewan
import com.example.aplikasipamproject.repositori.KuanRepository
import com.example.aplikasipamproject.repositori.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InsertHewanViewModel(
    private val kuanRepository: KuanRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var uiStateHewan by mutableStateOf(UIStateHewan())
        private set

    var owners by mutableStateOf<List<com.example.aplikasipamproject.modeldata.Pemilik>>(emptyList())
        private set

    // State untuk token
    val token = userPreferencesRepository.token
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    fun fetchOwners() {
        viewModelScope.launch {
            try {
                // Tunggu sampai token tersedia
                token.collect { tokenValue ->
                    if (tokenValue.isNotBlank()) {
                         owners = kuanRepository.getPemilik(tokenValue)
                    }
                }
            } catch (e: Exception) {
                // Silently fail or log
            }
        }
    }

    fun updateUiState(detailHewan: DetailHewan) {
        uiStateHewan = UIStateHewan(
            detailHewan = detailHewan,
            isEntryValid = validateInput(detailHewan)
        )
    }

    // PERBAIKAN: Modifikasi fungsi saveHewan
    fun saveHewan(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (!validateInput()) {
            onError("Semua kolom harus diisi.")
            return
        }

        viewModelScope.launch {
            try {
                // Ambil token langsung dari flow
                val currentToken = token.value
                
                // Pastikan token yang diberikan valid
                if (currentToken.isBlank()) {
                    throw Exception("Token otentikasi tidak valid. Silakan login ulang.")
                }
                kuanRepository.insertHewan(currentToken, uiStateHewan.detailHewan.toHewan())
                onSuccess() // Panggil callback sukses jika tidak ada error
            } catch (e: Exception) {
                onError(e.message ?: "Terjadi kesalahan saat menyimpan data.")
            }
        }
    }

    private fun validateInput(uiState: DetailHewan = uiStateHewan.detailHewan): Boolean {
        return with(uiState) {
            nama_hewan.isNotBlank() && jenis_hewan.isNotBlank() && id_pemilik.toString().isNotBlank() && id_pemilik != 0
        }
    }
}

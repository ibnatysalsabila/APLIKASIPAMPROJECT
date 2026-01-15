package com.example.aplikasipamproject.viewmodel.hewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.DetailHewan
import com.example.aplikasipamproject.modeldata.UIStateHewan
import com.example.aplikasipamproject.modeldata.toHewan
import com.example.aplikasipamproject.repositori.KuanRepository
import com.example.aplikasipamproject.uicontroller.route.DestinasiUpdateHewan
import kotlinx.coroutines.launch

class EditHewanViewModel(
    savedStateHandle: SavedStateHandle,
    private val kuanRepository: KuanRepository
) : ViewModel() {

    // Menyimpan state UI saat ini.
    var uiStateHewan by mutableStateOf(UIStateHewan())
        private set

    private val hewanId: Int = checkNotNull(savedStateHandle[DestinasiUpdateHewan.ID_HEWAN])

    var owners by mutableStateOf<List<com.example.aplikasipamproject.modeldata.Pemilik>>(emptyList())
        private set

    fun fetchOwners(token: String) {
        viewModelScope.launch {
            try {
                owners = kuanRepository.getPemilik(token)
            } catch (e: Exception) {
                // Silently fail or log
            }
        }
    }

    init {
        // Mengambil data hewan yang akan diedit saat ViewModel dibuat.
        viewModelScope.launch {
            uiStateHewan = kuanRepository.getHewan("YOUR_AUTH_TOKEN")
                .firstOrNull { it.id_hewan == hewanId }
                ?.let { hewan ->
                    UIStateHewan(
                        detailHewan = DetailHewan(
                            id_hewan = hewan.id_hewan,
                            nama_hewan = hewan.nama_hewan,
                            jenis_hewan = hewan.jenis_hewan,
                            id_pemilik = hewan.id_pemilik
                        ),
                        isEntryValid = true
                    )
                } ?: UIStateHewan()
        }
    }

    // Memperbarui state UI berdasarkan input pengguna.
    fun updateUiState(detailHewan: DetailHewan) {
        uiStateHewan = UIStateHewan(
            detailHewan = detailHewan,
            isEntryValid = validateInput(detailHewan)
        )
    }

    // Menyimpan perubahan data ke server.
    suspend fun updateHewan() {
        if (validateInput(uiStateHewan.detailHewan)) {
            // "YOUR_AUTH_TOKEN" harus diganti dengan token otentikasi yang valid.
            kuanRepository.updateHewan("YOUR_AUTH_TOKEN", hewanId, uiStateHewan.detailHewan.toHewan())
        }
    }

    // Validasi input.
    private fun validateInput(uiState: DetailHewan = uiStateHewan.detailHewan): Boolean {
        return with(uiState) {
            nama_hewan.isNotBlank() && jenis_hewan.isNotBlank() && id_pemilik > 0
        }
    }
}

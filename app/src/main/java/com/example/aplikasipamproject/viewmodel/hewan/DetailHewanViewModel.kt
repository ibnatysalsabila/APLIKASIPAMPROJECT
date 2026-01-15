package com.example.aplikasipamproject.viewmodel.hewan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.Hewan
import com.example.aplikasipamproject.repositori.KuanRepository
import com.example.aplikasipamproject.uicontroller.route.DestinasiDetailHewan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// UI State untuk halaman detail hewan.
data class HewanDetailUiState(
    val hewan: Hewan? = null
)

class DetailHewanViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: KuanRepository
) : ViewModel() {

    private val hewanId: Int = checkNotNull(savedStateHandle[DestinasiDetailHewan.ID_HEWAN])

    private val _hewanUiState = MutableStateFlow(HewanDetailUiState())
    val hewanUiState: StateFlow<HewanDetailUiState> = _hewanUiState.asStateFlow()

    init {
        getHewanById()
    }

    // Mengambil detail hewan berdasarkan ID dari repositori.
    fun getHewanById() {
        viewModelScope.launch {
            // "YOUR_AUTH_TOKEN" harus diganti dengan token otentikasi yang valid.
            val hewan = repository.getHewan("YOUR_AUTH_TOKEN")
                .firstOrNull { it.id_hewan == hewanId }
            _hewanUiState.value = HewanDetailUiState(hewan = hewan)
        }
    }

    // Menghapus data hewan yang sedang ditampilkan.
    suspend fun deleteHewan() {
        // "YOUR_AUTH_TOKEN" harus diganti dengan token otentikasi yang valid.
        repository.deleteHewan("YOUR_AUTH_TOKEN", hewanId)
    }
}

package com.example.aplikasipamproject.viewmodel.hewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.Hewan
import com.example.aplikasipamproject.repositori.KuanRepository
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * UI State khusus untuk halaman Home Hewan.
 * Ini bisa menampung daftar hewan (List<Hewan>).
 */
sealed interface HomeHewanUiState {
    data class Success(val data: List<Hewan>) : HomeHewanUiState
    object Error : HomeHewanUiState
    object Loading : HomeHewanUiState
}

class HomeHewanViewModel(private val repository: KuanRepository) : ViewModel() {

    /**
     * Menampung state UI untuk Home Hewan saat ini.
     */
    var hewanUIState: HomeHewanUiState by mutableStateOf(HomeHewanUiState.Loading)
        private set

    init {
        getHewan()
    }

    /**
     * Mengambil semua data hewan dari repositori.
     */
    fun getHewan() {
        viewModelScope.launch {
            hewanUIState = HomeHewanUiState.Loading
            hewanUIState = try {
                // Pastikan repository.getHewan() mengembalikan List<Hewan>
                HomeHewanUiState.Success(repository.getHewan("YOUR_AUTH_TOKEN")) // Ganti dengan token yang valid
            } catch (e: IOException) {
                HomeHewanUiState.Error
            }
        }
    }

    /**
     * Menghapus data hewan dari repositori dan memuat ulang data.
     */
    fun deleteHewan(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteHewan("YOUR_AUTH_TOKEN", id) // Ganti dengan token yang valid
                // Muat ulang data setelah berhasil menghapus
                getHewan()
            } catch (e: IOException) {
                // Handle error jika gagal menghapus, mungkin dengan menampilkan pesan
                // Untuk saat ini, kita muat ulang saja datanya
                getHewan()
            }
        }
    }
}

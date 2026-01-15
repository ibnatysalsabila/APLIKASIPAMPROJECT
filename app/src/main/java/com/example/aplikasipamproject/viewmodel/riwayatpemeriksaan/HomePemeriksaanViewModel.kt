package com.example.aplikasipamproject.viewmodel.riwayatpemeriksaan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.Pemeriksaan
import com.example.aplikasipamproject.repositori.KuanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomePemeriksaanUiState {
    object Loading : HomePemeriksaanUiState()
    data class Success(val pemeriksaan: List<Pemeriksaan>) : HomePemeriksaanUiState()
    data class Error(val message: String) : HomePemeriksaanUiState()
}

class HomePemeriksaanViewModel(private val kuanRepository: KuanRepository) : ViewModel() {

    var homePemeriksaanUiState: HomePemeriksaanUiState by mutableStateOf(HomePemeriksaanUiState.Loading)
        private set

    fun getPemeriksaan(token: String) {
        viewModelScope.launch {
            homePemeriksaanUiState = HomePemeriksaanUiState.Loading
            homePemeriksaanUiState = try {
                HomePemeriksaanUiState.Success(kuanRepository.getPemeriksaan(token))
            } catch (e: Exception) {
                android.util.Log.e("HomePemeriksaanVM", "Error fetching data", e)
                HomePemeriksaanUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
    fun deletePemeriksaan(token: String, id: Int) {
        viewModelScope.launch {
            try {
                kuanRepository.deletePemeriksaan(token, id)
                getPemeriksaan(token)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

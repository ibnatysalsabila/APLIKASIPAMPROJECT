package com.example.aplikasipamproject.viewmodel.layananmedis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.LayananMedis
import com.example.aplikasipamproject.repositori.KuanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeLayananUiState {
    object Loading : HomeLayananUiState()
    data class Success(val layanan: List<LayananMedis>) : HomeLayananUiState()
    data class Error(val message: String) : HomeLayananUiState()
}

class HomeLayananViewModel(private val kuanRepository: KuanRepository) : ViewModel() {

    var homeLayananUiState: HomeLayananUiState by mutableStateOf(HomeLayananUiState.Loading)
        private set

    fun getLayanan(token: String) {
        viewModelScope.launch {
            homeLayananUiState = HomeLayananUiState.Loading
            homeLayananUiState = try {
                HomeLayananUiState.Success(kuanRepository.getLayanan(token))
            } catch (e: Exception) {
                // Log the full error for debugging
                android.util.Log.e("HomeLayananViewModel", "Error fetching data", e)
                HomeLayananUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun deleteLayanan(token: String, id: Int) {
        viewModelScope.launch {
            try {
                kuanRepository.deleteLayanan(token, id)
                getLayanan(token)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
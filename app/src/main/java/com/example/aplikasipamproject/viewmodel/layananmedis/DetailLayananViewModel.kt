package com.example.aplikasipamproject.viewmodel.layananmedis

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.LayananMedis
import com.example.aplikasipamproject.repositori.KuanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailLayananUiState {
    object Loading : DetailLayananUiState()
    data class Success(val layanan: LayananMedis) : DetailLayananUiState()
    object Error : DetailLayananUiState()
}

class DetailLayananViewModel(
    savedStateHandle: SavedStateHandle,
    private val kuanRepository: KuanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailLayananUiState>(DetailLayananUiState.Loading)
    val uiState: StateFlow<DetailLayananUiState> = _uiState

    private val idLayanan: Int = checkNotNull(savedStateHandle["idLayanan"])

    fun getLayananById(token: String) {
        viewModelScope.launch {
            _uiState.value = DetailLayananUiState.Loading
            try {
                val listLayanan = kuanRepository.getLayanan(token)
                val layanan = listLayanan.find { it.id_layanan == idLayanan }
                if (layanan != null) {
                    _uiState.value = DetailLayananUiState.Success(layanan)
                } else {
                    _uiState.value = DetailLayananUiState.Error
                }
            } catch (e: Exception) {
                _uiState.value = DetailLayananUiState.Error
            }
        }
    }

    suspend fun deleteLayanan(token: String) {
        try {
            kuanRepository.deleteLayanan(token, idLayanan)
        } catch (e: Exception) {
            // handle error
        }
    }
}
package com.example.aplikasipamproject.viewmodel.riwayatpemeriksaan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.Pemeriksaan
import com.example.aplikasipamproject.repositori.KuanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailPemeriksaanUiState {
    object Loading : DetailPemeriksaanUiState()
    data class Success(val pemeriksaan: Pemeriksaan) : DetailPemeriksaanUiState()
    object Error : DetailPemeriksaanUiState()
}

class DetailPemeriksaanViewModel(
    savedStateHandle: SavedStateHandle,
    private val kuanRepository: KuanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailPemeriksaanUiState>(DetailPemeriksaanUiState.Loading)
    val uiState: StateFlow<DetailPemeriksaanUiState> = _uiState

    private val idPemeriksaan: Int = checkNotNull(savedStateHandle["idPemeriksaan"])

    fun getPemeriksaanById(token: String) {
        viewModelScope.launch {
            _uiState.value = DetailPemeriksaanUiState.Loading
            try {
                val listPemeriksaan = kuanRepository.getPemeriksaan(token)
                val pemeriksaan = listPemeriksaan.find { it.id_pemeriksaan == idPemeriksaan }
                if (pemeriksaan != null) {
                    _uiState.value = DetailPemeriksaanUiState.Success(pemeriksaan)
                } else {
                    _uiState.value = DetailPemeriksaanUiState.Error
                }
            } catch (e: Exception) {
                _uiState.value = DetailPemeriksaanUiState.Error
            }
        }
    }
}

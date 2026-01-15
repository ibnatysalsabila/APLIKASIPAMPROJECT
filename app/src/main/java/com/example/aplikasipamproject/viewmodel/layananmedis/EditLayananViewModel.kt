package com.example.aplikasipamproject.viewmodel.layananmedis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.DetailLayanan
import com.example.aplikasipamproject.modeldata.UIStateLayanan
import com.example.aplikasipamproject.modeldata.toLayanan
import com.example.aplikasipamproject.repositori.KuanRepository
import kotlinx.coroutines.launch

class EditLayananViewModel(
    savedStateHandle: SavedStateHandle,
    private val kuanRepository: KuanRepository
) : ViewModel() {

    var uiStateLayanan by mutableStateOf(UIStateLayanan())
        private set

    private val idLayanan: Int = checkNotNull(savedStateHandle["idLayanan"])

    fun getLayananById(token: String) {
        viewModelScope.launch {
            try {
                val listLayanan = kuanRepository.getLayanan(token)
                val layanan = listLayanan.find { it.id_layanan == idLayanan }
                layanan?.let {
                    uiStateLayanan = UIStateLayanan(
                        detailLayanan = DetailLayanan(
                            id_layanan = it.id_layanan,
                            nama_layanan = it.nama_layanan,
                            deskripsi = it.deskripsi,
                            biaya = it.biaya.toString()
                        ),
                        isEntryValid = true
                    )
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun updateUiState(detailLayanan: DetailLayanan) {
        uiStateLayanan = UIStateLayanan(
            detailLayanan = detailLayanan,
            isEntryValid = validateInput(detailLayanan)
        )
    }

    private fun validateInput(detail: DetailLayanan): Boolean {
        return detail.nama_layanan.isNotBlank() && 
               detail.deskripsi.isNotBlank() && 
               detail.biaya.isNotBlank()
    }

    suspend fun updateLayanan(token: String) {
        kuanRepository.updateLayanan(token, idLayanan, uiStateLayanan.detailLayanan.toLayanan())
    }
}
package com.example.aplikasipamproject.viewmodel.layananmedis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aplikasipamproject.modeldata.DetailLayanan
import com.example.aplikasipamproject.modeldata.UIStateLayanan
import com.example.aplikasipamproject.modeldata.toLayanan
import com.example.aplikasipamproject.repositori.KuanRepository
import java.io.IOException

class InsertLayananViewModel(private val kuanRepository: KuanRepository) : ViewModel() {

    var uiStateLayanan by mutableStateOf(UIStateLayanan())
        private set

    fun updateInsertLayananState(detailLayanan: DetailLayanan) {
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

    suspend fun insertLayanan(token: String) {
        try {
            kuanRepository.insertLayanan(token, uiStateLayanan.detailLayanan.toLayanan())
        } catch (e: IOException) {
            // Handle the exception
        }
    }
}

package com.example.aplikasipamproject.viewmodel.pemilikhewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.Pemilik
import com.example.aplikasipamproject.modeldata.toPemilik
import com.example.aplikasipamproject.repositori.KuanRepository
import kotlinx.coroutines.launch

class EditPemilikViewModel(private val kuanRepository: KuanRepository) : ViewModel() {
    var editUiState by mutableStateOf(InsertPemilikUiState())
        private set

    fun loadPemilikData(token: String, id: Int) {
        viewModelScope.launch {
            try {
                val response = kuanRepository.getPemilik(token)
                val pemilik = response.find { it.id_pemilik == id }
                pemilik?.let {
                    val event = PemilikEvent(
                        id_pemilik = it.id_pemilik,
                        nama = it.nama_pemilik,
                        noHP = it.no_hp,
                        email = it.email
                    )
                    editUiState = InsertPemilikUiState(
                        pemilikEvent = event,
                        isEntryValid = validateInput(event)
                    )
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun updateState(pemilikEvent: PemilikEvent) {
        val emailError = if (pemilikEvent.email.isNotEmpty() && !pemilikEvent.email.endsWith("@gmail.com")) {
            "Gunakan domain @gmail.com"
        } else null

        editUiState = InsertPemilikUiState(
            pemilikEvent = pemilikEvent,
            isEntryValid = validateInput(pemilikEvent),
            errorMessageEmail = emailError
        )
    }

    private fun validateInput(event: PemilikEvent): Boolean {
        return event.nama.isNotBlank() &&
                event.noHP.isNotBlank() &&
                event.email.isNotBlank() &&
                event.email.endsWith("@gmail.com")
    }

    suspend fun updatePemilik(token: String) {
        try {
            kuanRepository.updatePemilik(
                token = token,
                id = editUiState.pemilikEvent.id_pemilik,
                // Menggunakan fungsi toPemilik() yang ada di InsertPemilikViewModel
                d = editUiState.pemilikEvent.toPemilik()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

// PENTING: Jangan tulis ulang 'data class PemilikEvent' atau 'fun toPemilik()' di sini!
// Biarkan file ini menggunakan definisi yang sudah ada di InsertPemilikViewModel.kt
// agar tidak terjadi error "Conflicting overloads".
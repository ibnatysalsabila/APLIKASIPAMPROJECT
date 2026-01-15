package com.example.aplikasipamproject.viewmodel.pemilikhewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.toPemilik
import com.example.aplikasipamproject.repositori.KuanRepository
import kotlinx.coroutines.launch

class InsertPemilikViewModel(private val kuanRepository: KuanRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertPemilikUiState())
        private set

    fun updateInsertPemilikState(pemilikEvent: PemilikEvent) {
        val emailError = if (pemilikEvent.email.isNotEmpty() && !pemilikEvent.email.endsWith("@gmail.com")) {
            "Gunakan domain @gmail.com"
        } else null

        uiState = InsertPemilikUiState(
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

    fun insertPemilik(token: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                // Panggil Repository
                kuanRepository.insertPemilik(
                    token = token,
                    d = uiState.pemilikEvent.toPemilik()
                )
                // Jika tidak ada error, panggil callback sukses
                onSuccess()
            } catch (e: Exception) {
                // Log error ke terminal Android Studio
                println("DEBUG_KUAN_ERROR: ${e.message}")
            }
        }
    }
}

data class InsertPemilikUiState(
    val pemilikEvent: PemilikEvent = PemilikEvent(),
    val isEntryValid: Boolean = false,
    val errorMessageEmail: String? = null
)

data class PemilikEvent(
    val id_pemilik: Int = 0,
    val nama: String = "",
    val noHP: String = "",
    val email: String = ""
)
package com.example.aplikasipamproject.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aplikasipamproject.modeldata.LoginRequest
import com.example.aplikasipamproject.repositori.KuanRepository
import com.example.aplikasipamproject.repositori.UserPreferencesRepository

class LoginViewModel(
    private val kuanRepository: KuanRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun updateLoginState(username: String, password: String) {
        loginUiState = loginUiState.copy(
            username = username,
            password = password,
            isError = false, // Reset error saat user mulai mengetik ulang
            errorMessage = ""
        )
    }

    suspend fun login(): Boolean {
        // Validasi input sederhana sebelum menembak API
        if (loginUiState.username.isBlank() || loginUiState.password.isBlank()) {
            loginUiState = loginUiState.copy(
                isError = true,
                errorMessage = "Username dan Password tidak boleh kosong"
            )
            return false
        }

        loginUiState = loginUiState.copy(isLoading = true, isError = false)

        return try {
            val response = kuanRepository.login(
                LoginRequest(
                    username = loginUiState.username,
                    password = loginUiState.password
                )
            )

            if (response.success) {
                // Simpan token jika login sukses
                response.token?.let { token ->
                    userPreferencesRepository.saveToken(token)
                }
                loginUiState = loginUiState.copy(isLoading = false)
                true
            } else {
                loginUiState = loginUiState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = response.message
                )
                false
            }
        } catch (e: Exception) {
            loginUiState = loginUiState.copy(
                isLoading = false,
                isError = true,
                errorMessage = "Gagal terhubung ke server: ${e.localizedMessage ?: "Terjadi kesalahan"}"
            )
            false
        }
    }
}

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
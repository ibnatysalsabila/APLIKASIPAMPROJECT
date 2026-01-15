package com.example.aplikasipamproject.viewmodel.pemilikhewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasipamproject.modeldata.Pemilik
import com.example.aplikasipamproject.repositori.KuanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomePemilikUiState {
    object Loading : HomePemilikUiState()
    data class Success(val pemilik: List<Pemilik>) : HomePemilikUiState()
    object Error : HomePemilikUiState()
}

class HomePemilikViewModel(private val kuanRepository: KuanRepository) : ViewModel() {

    var homePemilikUiState: HomePemilikUiState by mutableStateOf(HomePemilikUiState.Loading)
        private set

    fun getPemilik(token: String) {
        viewModelScope.launch {
            homePemilikUiState = HomePemilikUiState.Loading
            homePemilikUiState = try {
                HomePemilikUiState.Success(kuanRepository.getPemilik(token))
            } catch (e: IOException) {
                HomePemilikUiState.Error
            } catch (e: HttpException) {
                HomePemilikUiState.Error
            }
        }
    }
    fun deletePemilik(token: String, id: Int) {
        viewModelScope.launch {
            try {
                kuanRepository.deletePemilik(token, id)
                getPemilik(token) // Refresh data
            } catch (e: IOException) {
                homePemilikUiState = HomePemilikUiState.Error
            } catch (e: HttpException) {
                homePemilikUiState = HomePemilikUiState.Error
            }
        }
    }
}
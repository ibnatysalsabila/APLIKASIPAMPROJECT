package com.example.aplikasipamproject.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.aplikasipamproject.repositori.KuanAplikasi
import com.example.aplikasipamproject.viewmodel.LoginViewModel
import com.example.aplikasipamproject.viewmodel.hewan.*
import com.example.aplikasipamproject.viewmodel.layananmedis.*
import com.example.aplikasipamproject.viewmodel.pemilikhewan.*
import com.example.aplikasipamproject.viewmodel.riwayatpemeriksaan.*

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { 
            LoginViewModel(
                kuanApp().container.kuanRepository,
                kuanApp().container.userPreferencesRepository
            ) 
        }

        // --- PEMILIK HEWAN ---
        initializer { HomePemilikViewModel(kuanApp().container.kuanRepository) }
        initializer { InsertPemilikViewModel(kuanApp().container.kuanRepository) }
        initializer { EditPemilikViewModel(kuanApp().container.kuanRepository) }
        // TAMBAHKAN BARIS INI:
        initializer { DetailPemilikViewModel(kuanApp().container.kuanRepository) }

        // --- HEWAN ---
        initializer { HomeHewanViewModel(kuanApp().container.kuanRepository) }
        initializer { 
            InsertHewanViewModel(
                kuanApp().container.kuanRepository,
                kuanApp().container.userPreferencesRepository
            ) 
        }
        initializer {
            EditHewanViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                kuanRepository = kuanApp().container.kuanRepository
            )
        }
        initializer { DetailHewanViewModel(this.createSavedStateHandle(), kuanApp().container.kuanRepository) }

        // --- LAYANAN MEDIS ---
        initializer { HomeLayananViewModel(kuanApp().container.kuanRepository) }
        initializer { InsertLayananViewModel(kuanApp().container.kuanRepository) }
        initializer {
            DetailLayananViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                kuanRepository = kuanApp().container.kuanRepository
            )
        }
        initializer {
            EditLayananViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                kuanRepository = kuanApp().container.kuanRepository
            )
        }

        // --- RIWAYAT PEMERIKSAAN ---
        initializer { HomePemeriksaanViewModel(kuanApp().container.kuanRepository) }
        initializer { InsertPemeriksaanViewModel(kuanApp().container.kuanRepository) }
        initializer {
            DetailPemeriksaanViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                kuanRepository = kuanApp().container.kuanRepository
            )
        }
        initializer {
            EditPemeriksaanViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                kuanRepository = kuanApp().container.kuanRepository
            )
        }

    }
}

fun CreationExtras.kuanApp(): KuanAplikasi =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KuanAplikasi)
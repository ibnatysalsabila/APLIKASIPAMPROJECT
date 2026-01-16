package com.example.aplikasipamproject.view.layananmedis

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipamproject.viewmodel.provider.PenyediaViewModel
import com.example.aplikasipamproject.viewmodel.layananmedis.DetailLayananViewModel
import com.example.aplikasipamproject.viewmodel.layananmedis.DetailLayananUiState
import com.example.aplikasipamproject.view.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailLayananScreen(
    idLayanan: Int,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: DetailLayananViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(idLayanan) {
        viewModel.getLayananById(token = "dummy_token")
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Detail Layanan",
                onBack = onBackClick,
                onRefresh = { viewModel.getLayananById(token = "dummy_token") }
            )
        }
    ) { innerPadding ->
        AppBackgroundColumn(
            modifier = modifier
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is DetailLayananUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is DetailLayananUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Gagal memuat data layanan.")
                    }
                }
                is DetailLayananUiState.Success -> {
                    val layanan = state.layanan
                    DetailCardKu {
                        DetailItemRow(icon = Icons.Default.Info, label = "ID Layanan", value = layanan.id_layanan.toString())
                        DetailItemRow(icon = Icons.Default.Info, label = "Nama Layanan", value = layanan.nama_layanan)
                        DetailItemRow(icon = Icons.Default.List, label = "Deskripsi", value = layanan.deskripsi ?: "-")
                        DetailItemRow(icon = Icons.Default.AttachMoney, label = "Biaya", value = "Rp ${layanan.biaya}")
                    }
                }
            }
        }
    }
}

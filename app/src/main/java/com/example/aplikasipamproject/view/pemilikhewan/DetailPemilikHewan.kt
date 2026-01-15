package com.example.aplikasipamproject.view.pemilikhewan

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipamproject.uicontroller.route.DestinasiDetailPemilik
import com.example.aplikasipamproject.viewmodel.provider.PenyediaViewModel
import com.example.aplikasipamproject.viewmodel.pemilikhewan.DetailPemilikViewModel
import com.example.aplikasipamproject.view.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPemilikHewan(
    idPemilik: Int,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: DetailPemilikViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.detailUiState

    LaunchedEffect(idPemilik) {
        viewModel.getDetailPemilik(token = "dummy_token", id = idPemilik)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Detail Pemilik",
                onBack = onBackClick,
                onRefresh = { viewModel.getDetailPemilik(token = "dummy_token", id = idPemilik) }
            )
        }
    ) { innerPadding ->
        AppBackgroundColumn(
            modifier = modifier
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                uiState.isError -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Gagal memuat data pemilik.")
                    }
                }
                uiState.pemilik != null -> {
                    val pemilik = uiState.pemilik
                    DetailCardKu {
                        DetailItemRow(icon = Icons.Default.AccountBox, label = "ID Pemilik", value = pemilik.id_pemilik.toString())
                        DetailItemRow(icon = Icons.Default.Person, label = "Nama Lengkap", value = pemilik.nama_pemilik)
                        DetailItemRow(icon = Icons.Default.Email, label = "Email", value = pemilik.email)
                        DetailItemRow(icon = Icons.Default.Phone, label = "Nomor Telepon", value = pemilik.no_hp)
                    }
                }
            }
        }
    }
}
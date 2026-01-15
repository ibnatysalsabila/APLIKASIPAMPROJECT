package com.example.aplikasipamproject.view.riwayatpemeriksaan

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
import com.example.aplikasipamproject.viewmodel.riwayatpemeriksaan.DetailPemeriksaanViewModel
import com.example.aplikasipamproject.viewmodel.riwayatpemeriksaan.DetailPemeriksaanUiState
import com.example.aplikasipamproject.view.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailRiwayatScreen(
    idPemeriksaan: Int,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: DetailPemeriksaanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(idPemeriksaan) {
        viewModel.getPemeriksaanById(token = "dummy_token")
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Detail Pemeriksaan",
                onBack = onBackClick,
                onRefresh = { viewModel.getPemeriksaanById(token = "dummy_token") }
            )
        }
    ) { innerPadding ->
        AppBackgroundColumn(
            modifier = modifier
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is DetailPemeriksaanUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is DetailPemeriksaanUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Gagal memuat data pemeriksaan.")
                    }
                }
                is DetailPemeriksaanUiState.Success -> {
                    val pemeriksaan = state.pemeriksaan
                    DetailCardKu {
                        DetailItemRow(icon = Icons.Default.ConfirmationNumber, label = "ID Riwayat", value = pemeriksaan.id_pemeriksaan.toString())
                        DetailItemRow(icon = Icons.Default.Pets, label = "Hewan", value = "${pemeriksaan.nama_hewan ?: "Unknown"} (ID: ${pemeriksaan.id_hewan})")
                        DetailItemRow(icon = Icons.Default.MedicalServices, label = "Layanan", value = "${pemeriksaan.nama_layanan ?: "Unknown"} (ID: ${pemeriksaan.id_layanan})")
                        DetailItemRow(icon = Icons.Default.DateRange, label = "Tanggal", value = formatDate(pemeriksaan.tgl_pemeriksaan))
                        DetailItemRow(icon = Icons.Default.EditNote, label = "Catatan Medis", value = pemeriksaan.catatan_medis)
                    }
                }
            }
        }
    }
}

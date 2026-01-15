package com.example.aplikasipamproject.view.riwayatpemeriksaan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipamproject.viewmodel.provider.PenyediaViewModel
import com.example.aplikasipamproject.viewmodel.riwayatpemeriksaan.HomePemeriksaanUiState
import com.example.aplikasipamproject.viewmodel.riwayatpemeriksaan.HomePemeriksaanViewModel
import com.example.aplikasipamproject.view.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRiwayatScreen(
    onDetailClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomePemeriksaanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val token = "your_token_here"
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var selectedRiwayatId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getPemeriksaan(token = token)
    }

    if (showDeleteConfirm && selectedRiwayatId != null) {
        val context = androidx.compose.ui.platform.LocalContext.current
        ConfirmDialogKu(
            onConfirm = {
                showDeleteConfirm = false
                viewModel.deletePemeriksaan(token, selectedRiwayatId!!)
                android.widget.Toast.makeText(context, "Data berhasil dihapus!", android.widget.Toast.LENGTH_SHORT).show()
                selectedRiwayatId = null
            },
            onDismiss = {
                showDeleteConfirm = false
                selectedRiwayatId = null
            },
            title = "Konfirmasi Hapus",
            message = "Apakah anda yakin ingin menghapus data ini?",
            icon = Icons.Default.Delete,
            confirmText = "Hapus"
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Daftar Riwayat Pemeriksaan",
                onBack = onBack,
                onRefresh = { viewModel.getPemeriksaan(token = token) }
            )
        }
    ) { innerPadding ->
        AppBackgroundColumn(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = viewModel.homePemeriksaanUiState) {
                is HomePemeriksaanUiState.Loading -> {
                    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is HomePemeriksaanUiState.Error -> {
                    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Terjadi Kesalahan Saat Memuat Data.")
                            Text("Error: ${state.message}", style = MaterialTheme.typography.bodySmall)
                            Button(onClick = { viewModel.getPemeriksaan(token) }) {
                                Text("Coba Lagi")
                            }
                        }
                    }
                }
                is HomePemeriksaanUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(state.pemeriksaan) { pemeriksaan ->
                            HomeCard(
                                title = pemeriksaan.nama_hewan ?: "Hewan ID: ${pemeriksaan.id_hewan}",
                                infoItems = listOf(
                                    Icons.Default.MedicalServices to "Layanan: ${pemeriksaan.nama_layanan ?: "Layanan ID: ${pemeriksaan.id_layanan}"}",
                                    Icons.Default.DateRange to "Tanggal: ${formatDate(pemeriksaan.tgl_pemeriksaan)}",
                                    Icons.Default.Description to "Catatan: ${pemeriksaan.catatan_medis}"
                                ),
                                onDetailClick = { onDetailClick(pemeriksaan.id_pemeriksaan) },
                                onEditClick = { onEditClick(pemeriksaan.id_pemeriksaan) },
                                onDeleteClick = { 
                                    selectedRiwayatId = pemeriksaan.id_pemeriksaan
                                    showDeleteConfirm = true
                                }
                            )
                        }
                    }
                }
            }

            AddButtonKu(
                text = "Tambah Riwayat",
                onClick = onAddClick,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}

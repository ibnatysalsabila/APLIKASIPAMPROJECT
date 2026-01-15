package com.example.aplikasipamproject.view.hewan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipamproject.modeldata.Hewan
import com.example.aplikasipamproject.uicontroller.route.DestinasiHewan
import com.example.aplikasipamproject.viewmodel.hewan.HomeHewanUiState
import com.example.aplikasipamproject.viewmodel.hewan.HomeHewanViewModel
import com.example.aplikasipamproject.viewmodel.provider.PenyediaViewModel
import com.example.aplikasipamproject.view.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHewanScreen(
    onNavigateToInsert: () -> Unit,
    onDetailClick: (Int) -> Unit,
    onNavigateToUpdate: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeHewanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var selectedHewanId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getHewan()
    }

    if (showDeleteConfirm && selectedHewanId != null) {
        val context = androidx.compose.ui.platform.LocalContext.current
        ConfirmDialogKu(
            onConfirm = {
                showDeleteConfirm = false
                viewModel.deleteHewan(selectedHewanId!!)
                android.widget.Toast.makeText(context, "Data berhasil dihapus!", android.widget.Toast.LENGTH_SHORT).show()
                selectedHewanId = null
            },
            onDismiss = {
                showDeleteConfirm = false
                selectedHewanId = null
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
                title = "Daftar Hewan",
                onBack = onBack,
                onRefresh = { viewModel.getHewan() }
            )
        }
    ) { innerPadding ->
        AppBackgroundColumn(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val uiState = viewModel.hewanUIState) {
                is HomeHewanUiState.Loading -> {
                    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is HomeHewanUiState.Error -> {
                    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Gagal memuat data", color = MaterialTheme.colorScheme.error)
                    }
                }
                is HomeHewanUiState.Success -> {
                    if (uiState.data.isEmpty()) {
                        Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "Tidak ada data hewan. Silakan tambahkan.",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            items(uiState.data) { hewan ->
                                HomeCard(
                                    title = hewan.nama_hewan,
                                    infoItems = listOf(
                                        Icons.Default.List to "Jenis: ${hewan.jenis_hewan}",
                                        Icons.Default.Person to "Owner: ${hewan.nama_pemilik ?: "ID: " + hewan.id_pemilik}"
                                    ),
                                    onDetailClick = { onDetailClick(hewan.id_hewan) },
                                    onEditClick = { onNavigateToUpdate(hewan.id_hewan) },
                                    onDeleteClick = { 
                                        selectedHewanId = hewan.id_hewan
                                        showDeleteConfirm = true
                                    }
                                )
                            }
                        }
                    }
                }
            }

            AddButtonKu(
                text = "Tambah Hewan",
                onClick = onNavigateToInsert,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}

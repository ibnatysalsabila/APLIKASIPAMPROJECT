package com.example.aplikasipamproject.view.layananmedis

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
import com.example.aplikasipamproject.viewmodel.layananmedis.HomeLayananUiState
import com.example.aplikasipamproject.viewmodel.layananmedis.HomeLayananViewModel
import com.example.aplikasipamproject.view.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLayananScreen(
    onDetailClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeLayananViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val token = "your_token_here"
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var selectedLayananId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getLayanan(token)
    }

    if (showDeleteConfirm && selectedLayananId != null) {
        val context = androidx.compose.ui.platform.LocalContext.current
        ConfirmDialogKu(
            onConfirm = {
                showDeleteConfirm = false
                viewModel.deleteLayanan(token, selectedLayananId!!)
                android.widget.Toast.makeText(context, "Data berhasil dihapus!", android.widget.Toast.LENGTH_SHORT).show()
                selectedLayananId = null
            },
            onDismiss = {
                showDeleteConfirm = false
                selectedLayananId = null
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
                title = "Daftar Layanan Medis",
                onBack = onBack,
                onRefresh = { viewModel.getLayanan(token) }
            )
        }
    ) { innerPadding ->
        AppBackgroundColumn(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = viewModel.homeLayananUiState) {
                is HomeLayananUiState.Loading -> {
                    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is HomeLayananUiState.Error -> {
                    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Terjadi Kesalahan Saat Memuat Data.")
                            Text("Error: ${state.message}", style = MaterialTheme.typography.bodySmall)
                            Button(onClick = { viewModel.getLayanan(token) }) {
                                Text("Coba Lagi")
                            }
                        }
                    }
                }
                is HomeLayananUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(state.layanan) { layanan ->
                            HomeCard(
                                title = layanan.nama_layanan,
                                infoItems = listOf(
                                    Icons.Default.Info to "Rp ${layanan.biaya}",
                                    Icons.Default.List to layanan.deskripsi
                                ),
                                onDetailClick = { onDetailClick(layanan.id_layanan) },
                                onEditClick = { onEditClick(layanan.id_layanan) },
                                onDeleteClick = {
                                    selectedLayananId = layanan.id_layanan
                                    showDeleteConfirm = true
                                }
                            )
                        }
                    }
                }
            }

            AddButtonKu(
                text = "Tambah Layanan",
                onClick = onAddClick,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}

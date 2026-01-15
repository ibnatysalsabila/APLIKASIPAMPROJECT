package com.example.aplikasipamproject.view.pemilikhewan

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
import com.example.aplikasipamproject.view.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipamproject.viewmodel.provider.PenyediaViewModel
import com.example.aplikasipamproject.viewmodel.pemilikhewan.HomePemilikUiState
import com.example.aplikasipamproject.viewmodel.pemilikhewan.HomePemilikViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePemilikHewan(
    onDetailClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomePemilikViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val token = "your_token_here" 
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var selectedPemilikId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getPemilik(token = token)
    }

    if (showDeleteConfirm && selectedPemilikId != null) {
        val context = androidx.compose.ui.platform.LocalContext.current
        ConfirmDialogKu(
            onConfirm = {
                showDeleteConfirm = false
                viewModel.deletePemilik(token, selectedPemilikId!!)
                android.widget.Toast.makeText(context, "Data berhasil dihapus!", android.widget.Toast.LENGTH_SHORT).show()
                selectedPemilikId = null
            },
            onDismiss = {
                showDeleteConfirm = false
                selectedPemilikId = null
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
                title = "Daftar Pemilik Hewan",
                onBack = onBack,
                onRefresh = { viewModel.getPemilik(token) }
            )
        }
    ) { innerPadding ->
        AppBackgroundColumn(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = viewModel.homePemilikUiState) {
                is HomePemilikUiState.Loading -> {
                    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is HomePemilikUiState.Error -> {
                    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Terjadi Kesalahan Saat Memuat Data.")
                    }
                }
                is HomePemilikUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(state.pemilik) { pemilik ->
                            HomeCard(
                                title = pemilik.nama_pemilik,
                                infoItems = listOf(
                                    Icons.Default.Phone to pemilik.no_hp,
                                    Icons.Default.Email to pemilik.email
                                ),
                                onDetailClick = { onDetailClick(pemilik.id_pemilik) },
                                onEditClick = { onEditClick(pemilik.id_pemilik) },
                                onDeleteClick = { 
                                    selectedPemilikId = pemilik.id_pemilik
                                    showDeleteConfirm = true
                                }
                            )
                        }
                    }
                }
            }
            
            AddButtonKu(
                text = "Tambah Pemilik",
                onClick = onAddClick,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}
package com.example.aplikasipamproject.view.hewan

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipamproject.modeldata.Hewan
import com.example.aplikasipamproject.uicontroller.route.DestinasiDetailHewan
import com.example.aplikasipamproject.viewmodel.provider.PenyediaViewModel
import com.example.aplikasipamproject.viewmodel.hewan.DetailHewanViewModel
import kotlinx.coroutines.launch
import com.example.aplikasipamproject.view.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailHewanScreen(
    onNavigateBack: () -> Unit,
    onNavigateToUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailHewanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.hewanUiState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Detail Hewan",
                onBack = onNavigateBack,
                onRefresh = { viewModel.getHewanById() }
            )
        }
    ) { innerPadding ->
        uiState.hewan?.let { hewan ->
            DetailHewanBody(
                hewan = hewan,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun DetailHewanBody(
    hewan: Hewan,
    modifier: Modifier = Modifier
) {
    AppBackgroundColumn(
        modifier = modifier
    ) {
        DetailCardKu {
            DetailItemRow(icon = Icons.Default.Info, label = "ID Hewan", value = hewan.id_hewan.toString())
            DetailItemRow(icon = Icons.Default.Pets, label = "Nama Hewan", value = hewan.nama_hewan)
            DetailItemRow(icon = Icons.Default.List, label = "Jenis Hewan", value = hewan.jenis_hewan)
            DetailItemRow(icon = Icons.Default.Person, label = "ID Pemilik", value = hewan.id_pemilik.toString())
        }
    }
}

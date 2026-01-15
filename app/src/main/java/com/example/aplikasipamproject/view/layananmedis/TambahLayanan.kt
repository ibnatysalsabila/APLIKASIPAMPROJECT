package com.example.aplikasipamproject.view.layananmedis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipamproject.uicontroller.route.DestinasiNavigasi
import com.example.aplikasipamproject.viewmodel.provider.PenyediaViewModel
import com.example.aplikasipamproject.viewmodel.layananmedis.InsertLayananViewModel
import com.example.aplikasipamproject.view.*
import kotlinx.coroutines.launch

object DestinasiEntryLayanan : DestinasiNavigasi {
    override val route = "tambah_layanan"
    override val titleRes = "Tambah Layanan"
}

@Composable
fun TambahLayananScreen(
    onBack: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertLayananViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiStateLayanan
    var showConfirmDialog by remember { mutableStateOf(false) }

    if (showConfirmDialog) {
        ConfirmDialogKu(
            onConfirm = {
                showConfirmDialog = false
                coroutineScope.launch {
                    viewModel.insertLayanan(token = "")
                    onNavigateBack()
                }
            },
            onDismiss = { showConfirmDialog = false },
            title = "Konfirmasi Simpan",
            message = "Apakah anda yakin ingin menambahkan data ini?"
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Tambah Layanan",
                onBack = onBack
            )
        }
    ) { innerPadding ->
        AppBackgroundColumn(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            CustomFormTextField(
                value = uiState.detailLayanan.nama_layanan,
                onValueChange = { viewModel.updateInsertLayananState(uiState.detailLayanan.copy(nama_layanan = InputFilter.lettersOnly(it))) },
                label = "Nama Layanan"
            )
            CustomFormTextField(
                value = uiState.detailLayanan.deskripsi,
                onValueChange = { viewModel.updateInsertLayananState(uiState.detailLayanan.copy(deskripsi = it)) },
                label = "Deskripsi"
            )
            CustomFormTextField(
                value = uiState.detailLayanan.biaya,
                onValueChange = { viewModel.updateInsertLayananState(uiState.detailLayanan.copy(biaya = InputFilter.digitsOnly(it))) },
                label = "Biaya (Rp)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            if (!uiState.isEntryValid) {
                Text(
                    text = "Harap isi semua data dengan benar",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            CustomFormButton(
                text = "Simpan",
                onClick = {
                    if (uiState.isEntryValid) {
                        showConfirmDialog = true
                    }
                },
                modifier = Modifier.padding(bottom = 24.dp),
                enabled = uiState.isEntryValid
            )
        }
    }
}

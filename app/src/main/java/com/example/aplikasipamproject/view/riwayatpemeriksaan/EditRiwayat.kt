package com.example.aplikasipamproject.view.riwayatpemeriksaan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipamproject.uicontroller.route.DestinasiUpdatePemeriksaan
import com.example.aplikasipamproject.viewmodel.provider.PenyediaViewModel
import com.example.aplikasipamproject.viewmodel.riwayatpemeriksaan.EditPemeriksaanViewModel
import com.example.aplikasipamproject.view.*
import kotlinx.coroutines.launch

@Composable
fun EditRiwayatScreen(
    onBack: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditPemeriksaanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiStatePemeriksaan
    val token = "" // Token handling centralized or passed
    var showConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchAllData(token)
    }

    if (showConfirmDialog) {
        ConfirmDialogKu(
            onConfirm = {
                showConfirmDialog = false
                coroutineScope.launch {
                    viewModel.updatePemeriksaan(token = token)
                    onNavigateBack()
                }
            },
            onDismiss = { showConfirmDialog = false },
            title = "Konfirmasi Ubah",
            message = "Apakah anda yakin ingin mengubah data ini?"
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Edit Riwayat",
                onBack = onBack,
                onRefresh = { viewModel.fetchAllData(token) }
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
            // content...
            Spacer(modifier = Modifier.height(16.dp))

            // 1. Pilih Hewan
        val selectedAnimal = viewModel.animals.find { it.id_hewan == viewModel.selectedAnimalId }
        DropDownKu(
            label = "Pilih Hewan",
            options = viewModel.animals.map { it.nama_hewan },
            selectedOption = selectedAnimal?.nama_hewan ?: "",
            onOptionSelected = { name ->
                val animal = viewModel.animals.find { it.nama_hewan == name }
                viewModel.selectedAnimalId = animal?.id_hewan ?: 0
                viewModel.updateUiState(uiState.detailPemeriksaan.copy(id_hewan = animal?.id_hewan ?: 0))
            }
        )

        // 2. Pilih Layanan
        val selectedService = viewModel.services.find { it.id_layanan == viewModel.selectedServiceId }
        DropDownKu(
            label = "Pilih Layanan",
            options = viewModel.services.map { it.nama_layanan },
            selectedOption = selectedService?.nama_layanan ?: "",
            onOptionSelected = { name ->
                val service = viewModel.services.find { it.nama_layanan == name }
                viewModel.selectedServiceId = service?.id_layanan ?: 0
                viewModel.updateUiState(uiState.detailPemeriksaan.copy(id_layanan = service?.id_layanan ?: 0))
            }
        )

        // 3. Tanggal Pemeriksaan with DatePicker
        DatePickerKu(
            label = "Tanggal Pemeriksaan",
            selectedDate = uiState.detailPemeriksaan.tgl_pemeriksaan,
            onDateSelected = { date ->
                viewModel.updateUiState(uiState.detailPemeriksaan.copy(tgl_pemeriksaan = date))
            }
        )

        CustomFormTextField(
            value = uiState.detailPemeriksaan.catatan_medis,
            onValueChange = { viewModel.updateUiState(uiState.detailPemeriksaan.copy(catatan_medis = it)) },
            label = "Catatan Medis"
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

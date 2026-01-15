package com.example.aplikasipamproject.view.hewan

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipamproject.modeldata.DetailHewan
import com.example.aplikasipamproject.viewmodel.provider.PenyediaViewModel
import com.example.aplikasipamproject.viewmodel.hewan.InsertHewanViewModel
import com.example.aplikasipamproject.view.*

@Composable
fun InsertHewanScreen(
    onBack: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertHewanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val context = LocalContext.current
    val uiState = viewModel.uiStateHewan
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchOwners()
    }

    if (showConfirmDialog) {
        ConfirmDialogKu(
            onConfirm = {
                showConfirmDialog = false
                isLoading = true
                viewModel.saveHewan(
                    onSuccess = {
                        isLoading = false
                        Toast.makeText(context, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                        onNavigateBack()
                    },
                    onError = { errorMessage ->
                        isLoading = false
                        Toast.makeText(context, "Gagal: $errorMessage", Toast.LENGTH_LONG).show()
                    }
                )
            },
            onDismiss = { showConfirmDialog = false },
            title = "Konfirmasi Simpan",
            message = "Apakah anda yakin ingin menambahkan data ini?",
            icon = Icons.Default.Add
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Tambah Hewan",
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

            FormInputHewan(
                detailHewan = uiState.detailHewan,
                owners = viewModel.owners,
                onValueChange = viewModel::updateUiState,
                modifier = Modifier.fillMaxWidth()
            )

            if (!uiState.isEntryValid && !isLoading) {
                Text(
                    text = "Harap lengkapi semua data",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            CustomFormButton(
                text = if (isLoading) "Menyimpan..." else "Simpan",
                onClick = {
                    if (uiState.isEntryValid) {
                        showConfirmDialog = true
                    }
                },
                modifier = Modifier.padding(bottom = 24.dp),
                enabled = uiState.isEntryValid && !isLoading
            )
        }
    }
}

@Composable
fun FormInputHewan(
    detailHewan: DetailHewan,
    owners: List<com.example.aplikasipamproject.modeldata.Pemilik>,
    onValueChange: (DetailHewan) -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        CustomFormTextField(
            value = detailHewan.nama_hewan,
            onValueChange = { onValueChange(detailHewan.copy(nama_hewan = InputFilter.lettersOnly(it))) },
            label = "Nama Hewan",
            enabled = enabled
        )

        DropDownKu(
            label = "Jenis Hewan",
            options = listOf("Anjing", "Kucing"),
            selectedOption = detailHewan.jenis_hewan,
            onOptionSelected = { onValueChange(detailHewan.copy(jenis_hewan = it)) }
        )

        val ownerOptions = owners.map { it.nama_pemilik }
        val selectedOwnerName = owners.find { it.id_pemilik == detailHewan.id_pemilik }?.nama_pemilik ?: ""

        DropDownKu(
            label = "Nama Pemilik",
            options = ownerOptions,
            selectedOption = selectedOwnerName,
            onOptionSelected = { name ->
                val selectedOwner = owners.find { it.nama_pemilik == name }
                if (selectedOwner != null) {
                    onValueChange(detailHewan.copy(id_pemilik = selectedOwner.id_pemilik))
                }
            }
        )
    }
}

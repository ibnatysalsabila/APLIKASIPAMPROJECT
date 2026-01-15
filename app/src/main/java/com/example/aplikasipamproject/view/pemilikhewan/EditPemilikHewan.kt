package com.example.aplikasipamproject.view.pemilikhewan

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipamproject.view.*
import com.example.aplikasipamproject.viewmodel.provider.PenyediaViewModel
import com.example.aplikasipamproject.viewmodel.pemilikhewan.EditPemilikViewModel
import kotlinx.coroutines.launch

@Composable
fun EditPemilikHewan(
    idPemilik: Int,
    onBack: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditPemilikViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.editUiState
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var showConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(idPemilik) {
        viewModel.loadPemilikData(token = "", id = idPemilik)
    }

    if (showConfirmDialog) {
        ConfirmDialogKu(
            onConfirm = {
                showConfirmDialog = false
                coroutineScope.launch {
                    viewModel.updatePemilik(token = "")
                    Toast.makeText(context, "Data berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    onNavigateBack()
                }
            },
            onDismiss = { showConfirmDialog = false },
            title = "Konfirmasi Ubah",
            message = "Apakah anda yakin ingin mengubah data ini?",
            icon = Icons.Default.Edit
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Edit Pemilik",
                onBack = onBack,
                onRefresh = { viewModel.loadPemilikData(token = "", id = idPemilik) }
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
                value = uiState.pemilikEvent.nama,
                onValueChange = { viewModel.updateState(uiState.pemilikEvent.copy(nama = InputFilter.lettersOnly(it))) },
                label = "Nama Pemilik",
                modifier = Modifier.fillMaxWidth()
            )
            CustomFormTextField(
                value = uiState.pemilikEvent.noHP,
                onValueChange = { viewModel.updateState(uiState.pemilikEvent.copy(noHP = InputFilter.digitsOnly(it))) },
                label = "Nomor Telepon",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )
            CustomFormTextField(
                value = uiState.pemilikEvent.email,
                onValueChange = { viewModel.updateState(uiState.pemilikEvent.copy(email = it)) },
                label = "Email Address",
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errorMessageEmail != null,
                errorMessage = uiState.errorMessageEmail,
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Email)
            )

            if (!uiState.isEntryValid) {
                Text(
                    text = "Harap lengkapi semua data",
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

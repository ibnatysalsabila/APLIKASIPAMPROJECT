package com.example.aplikasipamproject.view.pemilikhewan

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipamproject.view.*
import com.example.aplikasipamproject.viewmodel.provider.PenyediaViewModel
import com.example.aplikasipamproject.viewmodel.pemilikhewan.InsertPemilikViewModel
import com.example.aplikasipamproject.viewmodel.pemilikhewan.PemilikEvent
import kotlinx.coroutines.launch

@Composable
fun InsertPemilikHewan(
    onBack: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPemilikViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()
    var showConfirmDialog by remember { mutableStateOf(false) }

    if (showConfirmDialog) {
        ConfirmDialogKu(
            onConfirm = {
                showConfirmDialog = false
                coroutineScope.launch {
                    viewModel.insertPemilik(
                        token = "",
                        onSuccess = { 
                            Toast.makeText(context, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                            onNavigateBack() 
                        }
                    )
                }
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
                title = "Tambah Pemilik",
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

            FormInputPemilik(
                pemilikEvent = uiState.pemilikEvent,
                onValueChange = viewModel::updateInsertPemilikState,
                emailError = uiState.errorMessageEmail,
                modifier = Modifier.fillMaxWidth()
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

@Composable
fun FormInputPemilik(
    pemilikEvent: PemilikEvent,
    modifier: Modifier = Modifier,
    onValueChange: (PemilikEvent) -> Unit = {},
    emailError: String? = null,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        CustomFormTextField(
            value = pemilikEvent.nama,
            onValueChange = { onValueChange(pemilikEvent.copy(nama = InputFilter.lettersOnly(it))) },
            label = "Nama Lengkap",
            enabled = enabled
        )
        CustomFormTextField(
            value = pemilikEvent.noHP,
            onValueChange = { onValueChange(pemilikEvent.copy(noHP = InputFilter.digitsOnly(it))) },
            label = "Nomor Telepon",
            enabled = enabled,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
        )
        CustomFormTextField(
            value = pemilikEvent.email,
            onValueChange = { onValueChange(pemilikEvent.copy(email = it)) },
            label = "Email Address",
            enabled = enabled,
            isError = emailError != null,
            errorMessage = emailError,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Email)
        )
    }
}

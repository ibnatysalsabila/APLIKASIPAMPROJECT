package com.example.aplikasipamproject.view.hewan

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
import com.example.aplikasipamproject.viewmodel.hewan.EditHewanViewModel
import kotlinx.coroutines.launch

@Composable
fun EditHewanScreen(
    onBack: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditHewanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiStateHewan
    val owners = viewModel.owners
    val token = "" // Token handling centralized or passed
    var showConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchOwners(token)
    }

    if (showConfirmDialog) {
        ConfirmDialogKu(
            onConfirm = {
                showConfirmDialog = false
                coroutineScope.launch {
                    viewModel.updateHewan()
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
                title = "Edit Hewan",
                onBack = onBack,
                onRefresh = { viewModel.fetchOwners(token) }
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
                owners = owners,
                onValueChange = viewModel::updateUiState,
                modifier = Modifier.fillMaxWidth()
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

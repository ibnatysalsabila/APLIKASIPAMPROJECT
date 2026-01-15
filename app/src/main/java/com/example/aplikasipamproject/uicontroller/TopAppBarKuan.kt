package com.example.aplikasipamproject.uicontroller

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Composable yang menampilkan top app bar dan dapat digulir.
 *
 * @param title Judul yang akan ditampilkan di tengah TopAppBar.
 * @param canNavigateBack Boolean untuk menentukan apakah tombol kembali ditampilkan.
 * @param modifier Modifier untuk kustomisasi.
 * @param scrollBehavior Perilaku gulir untuk TopAppBar.
 * @param navigateUp Aksi yang dijalankan saat tombol kembali ditekan.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarKuan(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Kembali"
                    )
                }
            }
        }
    )
}

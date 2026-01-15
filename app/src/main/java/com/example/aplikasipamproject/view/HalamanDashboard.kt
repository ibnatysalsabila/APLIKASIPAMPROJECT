package com.example.aplikasipamproject.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasipamproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDashboard(
    onPemilikClick: () -> Unit,
    onHewanClick: () -> Unit,
    onLayananClick: () -> Unit,
    onRiwayatClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    AppBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "KLINIK KUAN",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(194, 61, 107) // Custom pinkish color from reference
                        )
                    },
                    actions = {
                        Surface(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable { onProfileClick() },
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFF5F5F5)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    "Admin",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray)
                                ) {
                                    Icon(
                                        Icons.Default.Person,
                                        null,
                                        modifier = Modifier.align(Alignment.Center),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
                    NavigationBarItem(
                        selected = true,
                        onClick = { },
                        icon = { Icon(Icons.Default.Home, null, tint = Color(194, 61, 107)) },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = onProfileClick,
                        icon = { Icon(Icons.Default.Person, null) },
                        label = { Text("Profile") }
                    )
                }
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Hero Banner
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(180.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF90A4AE)) // Muted blue
                ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.img_hero_banner),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .weight(1.2f)
                                .fillMaxHeight()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Selamat Datang, Admin Klinik Kuan",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Klinik Pilihan Terpercaya",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Grid Items
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ColoredMenuCard(
                            title = "Pemilik Hewan",
                            subtitle = "Data Pemilik",
                            imageRes = R.drawable.img_pemilik,
                            backgroundColor = Color(0xFFE8EAF6), // Lavender
                            modifier = Modifier.weight(1f),
                            onClick = onPemilikClick
                        )
                        ColoredMenuCard(
                            title = "Hewan",
                            subtitle = "Data Hewan",
                            imageRes = R.drawable.img_hewan,
                            backgroundColor = Color(0xFFFCE4EC), // Pinkish
                            modifier = Modifier.weight(1f),
                            onClick = onHewanClick
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ColoredMenuCard(
                            title = "Layanan Medis",
                            subtitle = "Data Layanan",
                            imageRes = R.drawable.img_layanan,
                            backgroundColor = Color(0xFFFFF3E0), // Peach
                            modifier = Modifier.weight(1f),
                            onClick = onLayananClick
                        )
                        ColoredMenuCard(
                            title = "Riwayat",
                            subtitle = "Riwayat Pemeriksaan",
                            imageRes = R.drawable.img_riwayat,
                            backgroundColor = Color(0xFFE0F7FA), // Light Blue
                            modifier = Modifier.weight(1f),
                            onClick = onRiwayatClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColoredMenuCard(
    title: String,
    subtitle: String,
    imageRes: Int,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(0.95f)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

package com.example.aplikasipamproject.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasipamproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onHomeClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryPink = Color(194, 61, 107)
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        ConfirmDialogKu(
            onConfirm = {
                showLogoutDialog = false
                onLogoutClick()
            },
            onDismiss = { showLogoutDialog = false },
            title = "Konfirmasi Logout",
            message = "Apakah anda yakin ingin Logout?"
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                NavigationBarItem(
                    selected = false,
                    onClick = onHomeClick,
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Person, contentDescription = null, tint = primaryPink) },
                    label = { Text("Profile") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryPink,
                        indicatorColor = primaryPink.copy(alpha = 0.1f)
                    )
                )
            }
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
    AppBackground(
        modifier = modifier.padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Profile",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = primaryPink,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Circular Logo Profile
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .shadow(10.dp, CircleShape) // Shadow first
                    .clip(CircleShape) // Clip content
                    .background(Color.White) // Background
                    .border(2.dp, primaryPink.copy(alpha = 0.5f), CircleShape), // Border
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Icon",
                    modifier = Modifier.size(100.dp),
                    tint = primaryPink
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Information Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.95f) // More solid but still soft
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    ProfileInfoItem(label = "ROLE", value = "ADMIN")
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.08f), thickness = 1.dp)
                    ProfileInfoItem(label = "USERNAME", value = "ADMIN")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Logout Button
            Button(
                onClick = { showLogoutDialog = true },
                modifier = Modifier
                    .fillMaxWidth() // Full width as requested
                    .height(50.dp) // Maintain consistent height
                    .shadow(4.dp, RoundedCornerShape(25.dp)),
                shape = RoundedCornerShape(25.dp), // Adjusted radius
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Text(
                    text = "LOGOUT",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                color = Color.Gray,
                fontSize = 11.sp, // Slightly smaller for premium look
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1A1A1A), // Near black for better contrast
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

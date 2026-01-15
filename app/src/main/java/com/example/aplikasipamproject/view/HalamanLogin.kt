package com.example.aplikasipamproject.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipamproject.R
import com.example.aplikasipamproject.viewmodel.LoginViewModel
import com.example.aplikasipamproject.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun HalamanLogin(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val loginUiState = viewModel.loginUiState
    val coroutineScope = rememberCoroutineScope()
    var passwordVisible by remember { mutableStateOf(false) }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFEECE9), // Peach/Salmon
            Color(0xFFFEECE9), // Peach/Salmon
            Color(0xFFC5E1F5)  // Light Blue
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            // Logo Section
            Image(
                painter = painterResource(id = R.drawable.logokuan),
                contentDescription = "Logo Kuan",
                modifier = Modifier
                    .size(450.dp) // User's requested size
                    .offset(y = 10.dp), // Reduced downward offset
                contentScale = ContentScale.Fit
            )


            // Card Container
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-60).dp), // Pull card up to close gap
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, top = 40.dp, bottom = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Username/Email Field
                    OutlinedTextField(
                        value = loginUiState.username,
                        onValueChange = { viewModel.updateLoginState(it, loginUiState.password) },
                        placeholder = { Text("username", color = Color.Gray) },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFC5E1F5),
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedContainerColor = Color(0xFFF8FBFF),
                            unfocusedContainerColor = Color(0xFFF8FBFF)
                        ),
                        singleLine = true
                    )

                    // Password Field
                    OutlinedTextField(
                        value = loginUiState.password,
                        onValueChange = { viewModel.updateLoginState(loginUiState.username, it) },
                        placeholder = { Text("password", color = Color.Gray) },
                        leadingIcon = { Icon(Icons.Default.Key, contentDescription = null, tint = Color.Gray) },
                        trailingIcon = {
                            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = null, tint = Color.Gray)
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFC5E1F5),
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedContainerColor = Color(0xFFF8FBFF),
                            unfocusedContainerColor = Color(0xFFF8FBFF)
                        ),
                        singleLine = true
                    )

                    if (loginUiState.isError) {
                        Text(
                            text = loginUiState.errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 4.dp),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Login Button
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                if (viewModel.login()) onLoginSuccess()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0066FF), // Vibrant Blue
                            contentColor = Color.White
                        ),
                        enabled = !loginUiState.isLoading
                    ) {
                        if (loginUiState.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                        } else {
                            Text("LOGIN", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun CloudIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = null,
        modifier = modifier,
        tint = Color(0xFFE3F2FD)
    )
}

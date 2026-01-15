package com.example.aplikasipamproject.uicontroller.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aplikasipamproject.view.HalamanDashboard
import com.example.aplikasipamproject.view.HalamanLogin
import com.example.aplikasipamproject.view.ProfileScreen
import com.example.aplikasipamproject.view.hewan.DetailHewanScreen
import com.example.aplikasipamproject.view.hewan.EditHewanScreen
import com.example.aplikasipamproject.view.hewan.HomeHewanScreen
import com.example.aplikasipamproject.view.hewan.InsertHewanScreen
import com.example.aplikasipamproject.view.pemilikhewan.DetailPemilikHewan
import com.example.aplikasipamproject.view.pemilikhewan.EditPemilikHewan
import com.example.aplikasipamproject.view.pemilikhewan.HomePemilikHewan
import com.example.aplikasipamproject.view.pemilikhewan.InsertPemilikHewan
import com.example.aplikasipamproject.view.layananmedis.*
import com.example.aplikasipamproject.view.riwayatpemeriksaan.*

@Composable
fun KuanApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    PetaNavigasi(navController = navController)
}

@Composable
fun PetaNavigasi(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiLogin.route
    ) {
        // 1. Halaman Login
        composable(DestinasiLogin.route) {
            HalamanLogin(onLoginSuccess = {
                navController.navigate("dashboard") {
                    popUpTo(DestinasiLogin.route) { inclusive = true }
                }
            })
        }

        composable("dashboard") {
            HalamanDashboard(
                onPemilikClick = { navController.navigate(DestinasiPemilik.route) },
                onHewanClick = { navController.navigate(DestinasiHewan.route) },
                onLayananClick = { navController.navigate(DestinasiLayanan.route) },
                onRiwayatClick = { navController.navigate(DestinasiPemeriksaan.route) },
                onProfileClick = { navController.navigate("profile") }
            )
        }

        composable("profile") {
            ProfileScreen(
                onHomeClick = {
                    navController.navigate("dashboard") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            )
        }

        // 2. Halaman Home/Dashboard Pemilik
        composable(DestinasiPemilik.route) {
            HomePemilikHewan(
                onAddClick = {
                    navController.navigate(DestinasiEntryPemilik.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailPemilik.route}/$id")
                },
                onEditClick = { id ->
                    navController.navigate("${DestinasiUpdatePemilik.route}/$id")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = DestinasiEntryPemilik.route) {
            InsertPemilikHewan(
                onBack = { navController.popBackStack() },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = DestinasiDetailPemilik.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailPemilik.ID_PEMILIK) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(DestinasiDetailPemilik.ID_PEMILIK) ?: 0
            DetailPemilikHewan(
                idPemilik = id,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiUpdatePemilik.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdatePemilik.ID_PEMILIK) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(DestinasiUpdatePemilik.ID_PEMILIK) ?: 0
            EditPemilikHewan(
                idPemilik = id,
                onBack = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // 1. Halaman Home Hewan
        // ... inside PetaNavigasi composable

// 1. Halaman Home Hewan
        composable(route = DestinasiHewan.route) {
            HomeHewanScreen(
                onNavigateToInsert = {
                    navController.navigate(DestinasiEntryHewan.route)
                },
                onDetailClick = { idHewan ->
                    navController.navigate("${DestinasiDetailHewan.route}/$idHewan")
                },
                onNavigateToUpdate = { idHewan ->
                    navController.navigate("${DestinasiUpdateHewan.route}/$idHewan")
                },
                // Add this line to fix the error
                onBack = { navController.popBackStack() }
            )
        }

// ... rest of the code

        composable(route = DestinasiEntryHewan.route) {
            InsertHewanScreen(
                onBack = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // In C:/Users/USER/APLIKASIPAMPROJECT/app/src/main/java/com/example/aplikasipamproject/uicontroller/PetaNavigasi.kt

// ...
        composable(
            route = DestinasiDetailHewan.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailHewan.ID_HEWAN) {
                type = NavType.IntType
            })
        ) {
            DetailHewanScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToUpdate = { idHewan ->
                    navController.navigate("${DestinasiUpdateHewan.route}/$idHewan")
                }
                // The 'onBack' parameter was redundant and has been removed.
            )
        }
//...


        composable(
            route = DestinasiUpdateHewan.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdateHewan.ID_HEWAN) {
                type = NavType.IntType
            })
        ) {
            EditHewanScreen(
                onBack = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- LAYANAN MEDIS ---
        composable(DestinasiLayanan.route) {
            HomeLayananScreen(
                onDetailClick = { id -> navController.navigate("${DestinasiDetailLayanan.route}/$id") },
                onEditClick = { id -> navController.navigate("${DestinasiUpdateLayanan.route}/$id") },
                onAddClick = { navController.navigate(DestinasiEntryLayanan.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(DestinasiEntryLayanan.route) {
            // Adjust to TambahLayananScreen if that's the correct name in your codebase
            TambahLayananScreen(
                onBack = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiDetailLayanan.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailLayanan.ID_LAYANAN) { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(DestinasiDetailLayanan.ID_LAYANAN) ?: 0
            DetailLayananScreen(
                idLayanan = id,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiUpdateLayanan.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdateLayanan.ID_LAYANAN) { type = NavType.IntType })
        ) {
            EditLayananScreen(
                onBack = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- RIWAYAT PEMERIKSAAN ---
        composable(DestinasiPemeriksaan.route) {
            HomeRiwayatScreen(
                onDetailClick = { id -> navController.navigate("${DestinasiDetailPemeriksaan.route}/$id") },
                onEditClick = { id -> navController.navigate("${DestinasiUpdatePemeriksaan.route}/$id") },
                onAddClick = { navController.navigate(DestinasiEntryPemeriksaan.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(DestinasiEntryPemeriksaan.route) {
            TambahRiwayatScreen(
                onBack = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiDetailPemeriksaan.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailPemeriksaan.ID_PEMERIKSAAN) { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(DestinasiDetailPemeriksaan.ID_PEMERIKSAAN) ?: 0
            DetailRiwayatScreen(
                idPemeriksaan = id,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiUpdatePemeriksaan.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdatePemeriksaan.ID_PEMERIKSAAN) { type = NavType.IntType })
        ) {
            EditRiwayatScreen(
                onBack = { navController.popBackStack() },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
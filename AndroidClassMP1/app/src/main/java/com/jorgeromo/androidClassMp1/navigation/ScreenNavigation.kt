package com.jorgeromo.androidClassMp1.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenNavigation(val route: String, val label: String, val icon: ImageVector) {
    object Ids : ScreenNavigation("ids", "IDS", Icons.Default.Home)
    object FirstPartial : ScreenNavigation("first_partial", "1er Parcial", Icons.Default.List)
    object SecondPartial : ScreenNavigation("second_partial", "2do Parcial", Icons.Default.Star)
    object ThirdPartial : ScreenNavigation("third_partial", "3er Parcial", Icons.Default.Settings)

    // Rutas internas
    object Login : ScreenNavigation("login", "Login", Icons.Default.Person)
    object IMC : ScreenNavigation("imc", "IMC", Icons.Default.Favorite)
    object Sum : ScreenNavigation("sum", "Suma", Icons.Default.Add)
    object Temperature : ScreenNavigation("temperature", "Temperatura", Icons.Default.Thermostat)
    object StudentList : ScreenNavigation("students", "Estudiantes", Icons.Default.School)
    object Locations : ScreenNavigation("locations", "Ubicaciones", Icons.Default.LocationOn)
    object LottieAnimation : ScreenNavigation("lottie_animation", "Animación", Icons.Default.PlayArrow)
    object Home : ScreenNavigation("HomeRoute", "Home", Icons.Default.People)
    object QrCode : ScreenNavigation("QrCodeRoute", "Código QR", Icons.Default.QrCode)
    object Home2 : ScreenNavigation("home2", "Market", Icons.Default.Store)
}
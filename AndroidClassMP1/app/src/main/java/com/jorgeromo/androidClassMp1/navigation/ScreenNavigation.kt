package com.jorgeromo.androidClassMp1.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenNavigation(val route: String, val label: String, val icon: ImageVector? = null) {

    // Tabs principales (usadas en NavigationBar)
    object Ids : ScreenNavigation("ids", "IDS", Icons.Default.Home)
    object FirstPartial : ScreenNavigation("first_partial", "1er Parcial", Icons.Default.List)
    object SecondPartial : ScreenNavigation("second_partial", "2do Parcial", Icons.Default.Star)
    object ThirdPartial : ScreenNavigation("third_partial", "3er Parcial", Icons.Default.Settings)

    // Rutas internas (pantallas completas independientes)
    object Login : ScreenNavigation("login", "Login")
    object Register : ScreenNavigation("register", "Registro")
    object Home : ScreenNavigation("home", "Home")
    object IMC : ScreenNavigation("imc", "IMC")
    object Sum : ScreenNavigation("sum", "Suma")
    object Temperature : ScreenNavigation("temperature", "Temperatura")
    object StudentList : ScreenNavigation("students", "Estudiantes")
    object Locations : ScreenNavigation("locations", "Ubicaciones")
}


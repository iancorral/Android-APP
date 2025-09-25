package com.jorgeromo.androidClassMp1.secondpartial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jorgeromo.androidClassMp1.navigation.ScreenNavigation

@Composable
fun SecondPartialView(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Segundo Parcial Moviles I",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate(ScreenNavigation.QrCode.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Código QR")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate(ScreenNavigation.Home2.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir al Home con JSON")
        }
    }
}
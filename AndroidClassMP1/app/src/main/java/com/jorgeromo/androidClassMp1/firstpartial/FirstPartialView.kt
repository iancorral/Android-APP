package com.jorgeromo.androidClassMp1.firstpartial

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jorgeromo.androidClassMp1.navigation.ScreenNavigation

@Composable
fun FirstPartialView(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Primer Parcial",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate(ScreenNavigation.Login.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}

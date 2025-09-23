package com.jorgeromo.androidClassMp1.firstpartial.lottieanimation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun LottieAnimationView() {
    // Cargar la animación desde la carpeta raw
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(com.jorgeromo.androidClassMp1.R.raw.cat)
    )

    // Control del progreso con bucle infinito
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever // 🔁 Esto hace el loop infinito
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Animación en loop infinito",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(300.dp) // Ajusta el tamaño a tu gusto
        )
    }
}


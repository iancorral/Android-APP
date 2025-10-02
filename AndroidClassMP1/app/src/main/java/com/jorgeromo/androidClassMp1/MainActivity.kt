package com.jorgeromo.androidClassMp1

// --- INICIO: IMPORTACIONES AÑADIDAS ---
import android.Manifest // Para acceder al permiso
import android.content.pm.PackageManager // Para verificar el estado del permiso
import android.os.Build // Para verificar la versión de Android
// --- FIN: IMPORTACIONES AÑADIDAS ---

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
// --- INICIO: IMPORTACIONES AÑADIDAS ---
import androidx.activity.result.contract.ActivityResultContracts // Para el launcher del permiso
import androidx.core.content.ContextCompat // Para verificar el permiso
// --- FIN: IMPORTACIONES AÑADIDAS ---
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jorgeromo.androidClassMp1.utils.datastore.DataStoreManager
import com.jorgeromo.androidClassMp1.firstpartial.onboarding.viewmodel.OnboardingViewModel
import com.jorgeromo.androidClassMp1.firstpartial.onboarding.views.OnboardingView
import com.jorgeromo.androidClassMp1.navigation.TabBarNavigationView
import com.jorgeromo.androidClassMp1.ui.theme.AndroidClassMP1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    // --- INICIO: CÓDIGO AÑADIDO ---

    // 1. Declaramos el 'launcher' que mostrará el diálogo del sistema
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // El usuario concedió el permiso. ¡Perfecto!
        } else {
            // El usuario denegó el permiso. La app no podrá mostrar notificaciones.
        }
    }

    // 2. Creamos la función que verifica si es necesario pedir el permiso
    private fun askNotificationPermission() {
        // Solo aplica para Android 13 (Tiramisu) o versiones superiores
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Verificamos si el permiso AÚN NO ha sido concedido
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                // Si no está concedido, lanzamos el diálogo para solicitarlo
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    // --- FIN: CÓDIGO AÑADIDO ---


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- INICIO: CÓDIGO AÑADIDO ---
        // 3. Llamamos a nuestra función justo al iniciar la actividad
        askNotificationPermission()
        // --- FIN: CÓDIGO AÑADIDO ---

        enableEdgeToEdge()

        val ds = DataStoreManager(this)

        setContent {
            AndroidClassMP1Theme {
                val scope = rememberCoroutineScope()
                val vm: OnboardingViewModel = viewModel()

                val onboardingDone: Boolean? by ds.onboardingDoneFlow.collectAsState(initial = null)

                when (onboardingDone) {
                    null -> SplashLoader()
                    false -> OnboardingView(
                        viewModel = vm,
                        onFinish = {
                            scope.launch { ds.setOnboardingDone(true) }
                        }
                    )
                    true -> TabBarNavigationView()
                }
            }
        }
    }
}

@Composable
private fun SplashLoader() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
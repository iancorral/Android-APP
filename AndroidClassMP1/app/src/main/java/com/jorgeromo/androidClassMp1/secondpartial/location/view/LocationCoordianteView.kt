package com.jorgeromo.androidClassMp1.secondpartial.location.view

// Pega esto al inicio de tu archivo LocationCoordianteView.kt

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.jorgeromo.androidClassMp1.secondpartial.location.repository.LocationRepository
import com.jorgeromo.androidClassMp1.secondpartial.location.viewmodel.LocationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationCoordianteView() {
    val context = LocalContext.current

    // Instancias simples sin Hilt (puedes migrarlo a Hilt si ya lo usas)
    val viewModel = remember {
        LocationViewModel(LocationRepository(context.applicationContext))
    }
    val ui by viewModel.ui.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    val neededPerms = remember {
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    var hasLocationPermission by rememberSaveable { mutableStateOf(false) }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    result[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            hasLocationPermission = granted
        }

    // Pide permisos al entrar
    LaunchedEffect(Unit) { permissionLauncher.launch(neededPerms) }

    // Arranca updates al tener permisos
    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) viewModel.onPermissionGranted()
    }

    // Pausa/Reanuda con ciclo de vida
    DisposableEffect(lifecycleOwner, hasLocationPermission) {
        if (!hasLocationPermission) return@DisposableEffect onDispose { }
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> viewModel.onPermissionGranted()
                Lifecycle.Event.ON_STOP  -> viewModel.stopUpdates()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            viewModel.stopUpdates()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Coordenadas en vivo") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!hasLocationPermission) {
                Text("Se requiere el permiso de ubicación.")
                Spacer(Modifier.height(8.dp))
                Button(onClick = { permissionLauncher.launch(neededPerms) }) {
                    Text("Conceder permiso")
                }
            } else {
                val latText = ui.latitude?.let { "%.6f".format(it) } ?: "--"
                val lngText = ui.longitude?.let { "%.6f".format(it) } ?: "--"

                Text(
                    "Lat: $latText\nLng: $lngText",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(12.dp))
                val note =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                        "En Android 12+ el usuario puede elegir ubicación precisa o aproximada."
                    else "La ubicación se actualiza mientras la app está en primer plano."
                Text(note, style = MaterialTheme.typography.bodyMedium)

                ui.error?.let {
                    Spacer(Modifier.height(12.dp))
                    Text("Error: $it", color = MaterialTheme.colorScheme.error)
                }

                if (ui.latitude == null && ui.longitude == null && ui.error == null) {
                    Spacer(Modifier.height(12.dp))
                    CircularProgressIndicator()
                }
            }
        }
    }
}
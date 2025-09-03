package com.jorgeromo.androidClassMp1.firstpartial.login.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.FaceRetouchingNatural
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jorgeromo.androidClassMp1.R
import com.jorgeromo.androidClassMp1.firstpartial.login.model.network.AuthApi
import com.jorgeromo.androidClassMp1.firstpartial.login.model.network.RetrofitProvider
import com.jorgeromo.androidClassMp1.firstpartial.login.model.repository.AuthRepository
import com.jorgeromo.androidClassMp1.firstpartial.login.viewmodel.LoginViewModel
import com.jorgeromo.androidClassMp1.firstpartial.login.viewmodel.LoginViewModelFactory
import com.jorgeromo.androidClassMp1.navigation.ScreenNavigation

@Composable
fun LoginView(
    navController: NavHostController
) {
    // Crear instancia del API y repositorio
    val api = RetrofitProvider.retrofit.create(AuthApi::class.java)
    val repository = AuthRepository(api)
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(repository)
    )

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo ULSA optimizado
        Image(
            painter = painterResource(id = R.drawable.ulsalogo),
            contentDescription = "ULSA Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Sign In",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password con icono mostrar/ocultar
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = "Toggle Password Visibility")
                }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Login conectado a la API
        Button(
            onClick = { viewModel.login(email, password) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text("Sign In")
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Ícono Face ID limpio usando Material Icons
        IconButton(
            onClick = { /* TODO: lógica Face ID */ },
            modifier = Modifier.size(60.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.FaceRetouchingNatural,
                contentDescription = "Face ID",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Mostrar mensajes de error o éxito
        when {
            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
            uiState.successMessage != null -> {
                Text(
                    text = uiState.successMessage!!,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                // Navegar automáticamente al Home si todo salió bien
                LaunchedEffect(uiState.successMessage) {
                    navController.navigate(ScreenNavigation.Home.route) {
                        popUpTo(ScreenNavigation.Login.route) { inclusive = true }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texto de registro
        Text(
            text = buildAnnotatedString {
                append("Don't have an account? ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Sign Up here")
                }
            },
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(ScreenNavigation.Register.route)
                }
        )
    }
}

package com.jorgeromo.androidClassMp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jorgeromo.androidClassMp1.navigation.TabBarNavigationView
import com.jorgeromo.androidClassMp1.ui.theme.AndroidClassMP1Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jorgeromo.androidClassMp1.firstpartial.onboarding.viewmodel.OnboardingViewModel
import com.jorgeromo.androidClassMp1.firstpartial.onboarding.views.OnboardingView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidClassMP1Theme {
                val vm: OnboardingViewModel = viewModel()
                OnboardingView(viewModel = vm)
                // TabBarNavigationView()
            }
        }
    }
}
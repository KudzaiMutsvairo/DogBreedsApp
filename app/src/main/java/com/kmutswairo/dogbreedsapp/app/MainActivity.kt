package com.kmutswairo.dogbreedsapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kmutswairo.dogbreedsapp.presentation.MainScreen
import com.kmutswairo.dogbreedsapp.ui.theme.DogBreedsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogBreedsAppTheme {
                MainScreen()
            }
        }
    }
}

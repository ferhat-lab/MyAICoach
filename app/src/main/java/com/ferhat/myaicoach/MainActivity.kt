package com.ferhat.myaicoach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ferhat.myaicoach.navigation.AppNavHost
import com.ferhat.myaicoach.ui.theme.MyAICoachTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MyAICoachTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavHost()
                }
            }
        }
    }
}
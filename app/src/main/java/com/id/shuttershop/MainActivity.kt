package com.id.shuttershop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.id.shuttershop.ui.screen.MainContainer
import com.id.shuttershop.ui.theme.ShutterShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShutterShopTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContainer(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

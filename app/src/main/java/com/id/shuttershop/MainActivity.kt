package com.id.shuttershop

import android.app.LocaleManager
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.id.shuttershop.ui.screen.MainContainer
import com.id.shuttershop.ui.theme.ShutterShopTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode = viewModel.isDarkMode.collectAsState(initial = false)
            val isIndonesia by viewModel.isIndonesia.collectAsState()

            LaunchedEffect(key1 = isIndonesia) {
                setLanguage(isIndonesia)
            }

            ShutterShopTheme(
                darkTheme = isDarkMode.value
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContainer(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun setLanguage(isIndonesia: Boolean) {
        var languageTag = ""
        languageTag = when (isIndonesia) {
            true -> {
                "in"
            }

            false -> {
                "en"
            }
        }
        applicationContext.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList.forLanguageTags(languageTag)
    }
}

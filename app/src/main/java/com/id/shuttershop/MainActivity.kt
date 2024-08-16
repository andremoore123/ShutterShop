package com.id.shuttershop

import android.Manifest
import android.app.LocaleManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.id.shuttershop.ui.screen.MainContainer
import com.id.shuttershop.ui.theme.ShutterShopTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    private val requiredPermissions = arrayOf(
        Manifest.permission.POST_NOTIFICATIONS,
    )

    /**
     * If Not Granted, Show Not Granted with Toast
     */
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val deniedPermissions = permissions.filterValues { !it }.keys
        if (deniedPermissions.isNotEmpty()) {
            Toast.makeText(this, "Permissions denied: $deniedPermissions", Toast.LENGTH_SHORT)
                .show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request For Permission
        if (arePermissionsGranted().not()) {
            requestPermissions()
        }
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
                    MainContainer(modifier = Modifier.padding(innerPadding), uriData = intent.data)
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            applicationContext.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(languageTag)
        }
    }

    private fun arePermissionsGranted(): Boolean {
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(requiredPermissions)
    }
}

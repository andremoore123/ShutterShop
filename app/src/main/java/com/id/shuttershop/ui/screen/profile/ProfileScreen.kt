package com.id.shuttershop.ui.screen.profile;

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.components.switch.SwitchWithText
import com.id.shuttershop.ui.theme.AppTypography
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.dashedBorder

/**
 * Created by: andreputras.
 * Date: 01/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val isIndonesiaLanguage by viewModel.isIndonesiaLanguage.collectAsState()
    val userData by viewModel.userData.collectAsState()
    val languageFeatureEnabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchUserData()
    }

    ProfileContent(
        modifier = modifier,
        profileName = userData.name,
        profileEmail = userData.email,
        isDarkMode = isDarkMode,
        isIndonesia = isIndonesiaLanguage,
        onThemeSwitchChange = viewModel::setDarkMode,
        onLanguageSwitchChange = viewModel::setIndonesiaLanguage,
        onLogoutClick = viewModel::logout,
        languageFeatureEnabled = languageFeatureEnabled
    )
}

@Composable
internal fun ProfileContent(
    modifier: Modifier = Modifier,
    languageFeatureEnabled: Boolean,
    profileName: String = "",
    profileEmail: String = "",
    profileImageUrl: String = "",
    isDarkMode: Boolean = false,
    isIndonesia: Boolean = false,
    onThemeSwitchChange: (Boolean) -> Unit = {},
    onLanguageSwitchChange: (Boolean) -> Unit = {},
    onLogoutClick: (String) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 15.dp)
                .dashedBorder(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .size(120.dp)
                    .background(Color.Black)
            )
        }
        Text(
            text = profileName,
            style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Black)
        )
        Text(
            text = profileEmail,
            style = AppTypography.bodyMedium,
            modifier = Modifier.padding(top = 5.dp)
        )
        Column(
            modifier = Modifier.padding(top = 40.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SwitchWithText(
                firstText = stringResource(R.string.text_light),
                secondText = stringResource(R.string.text_dark),
                onCheckChange = onThemeSwitchChange,
                checked = isDarkMode
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SwitchWithText(
                    firstText = stringResource(R.string.text_en),
                    secondText = stringResource(R.string.text_id),
                    onCheckChange = onLanguageSwitchChange,
                    checked = isIndonesia,
                    enabled = languageFeatureEnabled
                )
                if (languageFeatureEnabled.not()) {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = stringResource(R.string.text_version_not_supported),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = DividerDefaults.color
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
        PrimaryButton(
            text = stringResource(R.string.text_logout),
            modifier = Modifier.padding(top = 40.dp),
            onClick = { onLogoutClick(profileEmail) }
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun ShowProfileScreenPreview() {
    ShutterShopTheme {
        ProfileContent(languageFeatureEnabled = false)
    }
}

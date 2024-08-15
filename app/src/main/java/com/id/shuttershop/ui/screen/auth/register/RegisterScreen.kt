package com.id.shuttershop.ui.screen.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.PrimaryTextField
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.components.button.PrimaryTextButton
import com.id.shuttershop.ui.components.state.LoadingState
import com.id.shuttershop.ui.theme.AppTypography
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.onError
import com.id.shuttershop.utils.onLoading
import com.id.shuttershop.utils.onSuccess
import kotlinx.coroutines.launch

/**
 * Created by: andreputras.
 * Date: 31/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit = {},
) {
    val registerUiState by viewModel.registerUiState.collectAsState()
    val messageValue by viewModel.messageValue.collectAsState()
    val nameValue by viewModel.nameValue.collectAsState()
    val emailValue by viewModel.emailValue.collectAsState()
    val passwordValue by viewModel.passwordValue.collectAsState()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = messageValue) {
        if (messageValue.isNotEmpty()) {
            scope.launch {
                snackBarHostState.showSnackbar(messageValue)
            }
        }
    }

    val registerEvent = RegisterEvent(
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailValueChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRegisterClick = {
            viewModel.register(nameValue, emailValue, passwordValue)
        },
        onLoginClick = navigateToLogin
    )

    Box(modifier = modifier.fillMaxSize()) {
        registerUiState.onLoading {
            LoadingState()
        }.onSuccess {
            LaunchedEffect(key1 = Unit) {
                viewModel.onMessageValueChange("Register Success")
                navigateToLogin()
            }
        }.onError {
            LaunchedEffect(key1 = Unit) {
                viewModel.onMessageValueChange(it.errorMessage)
            }
        }
        Scaffold(
            modifier = Modifier,
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            }
        ) {
            RegisterContent(
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
                nameValue = nameValue,
                emailValue = emailValue,
                passwordValue = passwordValue, registerEvent = registerEvent
            )
        }
    }
}

@Composable
internal fun RegisterContent(
    modifier: Modifier = Modifier,
    nameValue: String = "",
    emailValue: String = "",
    passwordValue: String = "",
    registerEvent: RegisterEvent,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(200.dp),
        )
        Text(
            text = stringResource(R.string.text_register), style = AppTypography.displayMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = stringResource(R.string.text_subtitle_register), style = AppTypography.titleLarge
        )
        PrimaryTextField(
            modifier = Modifier
                .padding(top = 30.dp),
            title = stringResource(R.string.text_name),
            value = nameValue, onTextChange = registerEvent.onNameChange
        )
        PrimaryTextField(
            modifier = Modifier.padding(vertical = 20.dp),
            title = stringResource(R.string.text_email),
            value = emailValue, onTextChange = registerEvent.onEmailChange
        )
        PrimaryTextField(
            modifier = Modifier.padding(bottom = 40.dp),
            title = stringResource(R.string.text_password),
            value = passwordValue, onTextChange = registerEvent.onPasswordChange,
            inputType = PrimaryTextField.PASSwORD
        )

        PrimaryButton(
            text = stringResource(id = R.string.text_register),
            modifier = Modifier.fillMaxWidth(), onClick = registerEvent.onRegisterClick
        )
        Row(
            modifier = Modifier.padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.text_have_account))
            PrimaryTextButton(
                text = stringResource(R.string.text_login_now), onClick = registerEvent.onLoginClick
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun ShowRegisterScreenPreview() {
    ShutterShopTheme {
        RegisterContent(
            registerEvent = RegisterEvent(), nameValue = "", emailValue = "", passwordValue = ""
        )
    }
}

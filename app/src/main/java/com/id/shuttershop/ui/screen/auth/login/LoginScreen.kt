package com.id.shuttershop.ui.screen.auth.login

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
import com.id.shuttershop.utils.OnHttpError
import com.id.shuttershop.utils.OnUnknownError
import com.id.shuttershop.utils.onError
import com.id.shuttershop.utils.onLoading
import com.id.shuttershop.utils.validation.ErrorValidation
import kotlinx.coroutines.launch

/**
 * Created by: andreputras.
 * Date: 31/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToRegister: () -> Unit = {},
) {
    val loginUiState by viewModel.loginUiState.collectAsState()
    val messageValue by viewModel.messageValue.collectAsState()
    val emailValue by viewModel.emailValue.collectAsState()
    val passwordValue by viewModel.passwordValue.collectAsState()
    val scope = rememberCoroutineScope()
    val emailValidation by viewModel.emailValidation.collectAsState()
    val passwordValidation by viewModel.passwordValidation.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = messageValue) {
        if (messageValue.isNotEmpty()) {
            scope.launch {
                snackBarHostState.showSnackbar(messageValue)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        loginUiState.run {
            onLoading {
                LoadingState()
            }
            onError { errorType ->
                errorType.OnHttpError {
                    when (it) {
                        400 -> {
                            viewModel.onMessageValueChange(stringResource(R.string.text_email_password_not_valid_error))
                        }

                        else -> {
                            viewModel.onMessageValueChange(stringResource(id = R.string.text_unknown_error))
                        }
                    }
                }
                errorType.OnUnknownError {
                    viewModel.onMessageValueChange(stringResource(id = R.string.text_unknown_error))
                }
                LaunchedEffect(key1 = Unit) {
                    viewModel.resetUiState()
                }
            }
        }
        Scaffold(
            modifier = Modifier,
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            }
        ) { innerPadding ->
            LoginContent(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                emailValue = emailValue ?: "",
                passwordValue = passwordValue ?: "",
                onEmailChange = viewModel::onEmailValueChange,
                onPasswordChange = viewModel::onPasswordChange,
                onLoginClick = {
                    viewModel.login(emailValue.orEmpty(), passwordValue.orEmpty())
                },
                onRegisterClick = navigateToRegister,
                emailValidation = emailValidation,
                passwordValidation = passwordValidation,
                onEntriesChange = viewModel::isValidEntries
            )
        }
    }
}

@Composable
internal fun LoginContent(
    modifier: Modifier = Modifier,
    emailValue: String = "",
    passwordValue: String = "",
    emailValidation: ErrorValidation?,
    passwordValidation: ErrorValidation?,
    onEntriesChange: (String, String) -> Boolean,
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
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
            text = stringResource(R.string.text_login), style = AppTypography.displayMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = stringResource(R.string.text_subtitle_login), style = AppTypography.titleLarge
        )
        PrimaryTextField(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .padding(top = 10.dp),
            title = stringResource(R.string.text_email),
            value = emailValue,
            onTextChange = onEmailChange,
            errorValidation = emailValidation
        )
        PrimaryTextField(
            modifier = Modifier.padding(bottom = 40.dp),
            title = stringResource(R.string.text_password),
            value = passwordValue,
            onTextChange = onPasswordChange,
            errorValidation = passwordValidation,
            inputType = PrimaryTextField.PASSwORD
        )

        PrimaryButton(
            text = stringResource(id = R.string.text_login),
            modifier = Modifier.fillMaxWidth(),
            onClick = onLoginClick,
            enabled = onEntriesChange(emailValue, passwordValue)
        )
        Row(
            modifier = Modifier.padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.text_no_account))
            PrimaryTextButton(
                text = stringResource(R.string.text_register_now),
                onClick = onRegisterClick,
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun ShowLoginScreenPreview() {
    ShutterShopTheme {
        LoginContent(
            emailValidation = null,
            passwordValidation = null,
            onEntriesChange = { _, _ -> false })
    }
}

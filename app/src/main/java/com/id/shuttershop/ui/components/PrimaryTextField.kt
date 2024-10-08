package com.id.shuttershop.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.shuttershop.ui.theme.AppTypography
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.validation.ErrorValidation
import com.id.shuttershop.utils.validation.getErrorString
import com.id.shuttershop.utils.validation.isError

enum class PrimaryTextField {
    PASSwORD,
    DEFAULT
}

@Composable
fun PrimaryTextField(
    modifier: Modifier = Modifier,
    title: String = "",
    value: String = "",
    errorValidation: ErrorValidation? = null,
    inputType: PrimaryTextField = PrimaryTextField.DEFAULT,
    onTextChange: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        AnimatedVisibility(visible = value.isNotEmpty()) {
            Text(
                text = title,
                style = AppTypography.titleMedium,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = { onTextChange(it) },
            label = {
                AnimatedVisibility(visible = value.isEmpty()) {
                    Text(text = title)
                }
            },
            textStyle = AppTypography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorValidation?.isError() ?: false,
            supportingText = {
                errorValidation?.getErrorString()?.let {
                    val messageResource = stringResource(id = it)
                    Text(
                        text = messageResource,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            },
            maxLines = 1,
            visualTransformation = if (inputType == PrimaryTextField.PASSwORD) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            }
        )
    }
}

@Composable
@Preview
internal fun ShowPrimaryTextFieldPreview() {
    ShutterShopTheme {
        PrimaryTextField(
            title = "Email",
        )
    }
}

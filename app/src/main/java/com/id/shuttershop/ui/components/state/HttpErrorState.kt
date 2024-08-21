package com.id.shuttershop.ui.components.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.shuttershop.R
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 15/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun HttpErrorState(
    modifier: Modifier = Modifier,
    errorCode: Int = 0,
    onRetryClick: () -> Unit,
) {
    Box(modifier = modifier
        .fillMaxSize()
        .padding(16.dp), contentAlignment = Alignment.Center) {
        when (errorCode) {
            404 -> {
                NotFoundErrorState(modifier = modifier)
            }

            else -> {
                UnknownErrorState(onRetryClick = onRetryClick)
            }
        }
    }
}

@Composable
internal fun NotFoundErrorState(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.aspectRatio(1.5F),
            painter = painterResource(id = R.drawable.empty_state),
            contentDescription = null
        )
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.text_not_found_error),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
@Preview
internal fun HttpErrorStatePreview() {
    ShutterShopTheme {
        HttpErrorState(onRetryClick = {})
    }
}
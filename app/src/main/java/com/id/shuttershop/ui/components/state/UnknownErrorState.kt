package com.id.shuttershop.ui.components.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 15/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun UnknownErrorState(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.aspectRatio(1.5F),
            painter = painterResource(id = R.drawable.unknown_error),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.text_unknown_error),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 20.dp)
        )
        PrimaryButton(
            text = "Retry",
            modifier = Modifier.padding(top = 16.dp),
            onClick = onRetryClick
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun UnknownErrorStatePreview() {
    ShutterShopTheme {
        UnknownErrorState()
    }
}
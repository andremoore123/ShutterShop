package com.id.shuttershop.ui.components.card.transaction

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.id.shuttershop.ui.components.state.AnimatedSuccess
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun PaymentRatingBorder(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Box(modifier = modifier.padding(horizontal = 16.dp)) {
        AnimatedSuccess(
            modifier
                .size(150.dp)
                .align(Alignment.TopCenter)
                .zIndex(10F)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 75.dp)
                .border(2.dp, DividerDefaults.color, RoundedCornerShape(16.dp))
                .padding(top = 25.dp)
        ) {
            content()
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun PaymentRatingBorderPreview() {
    ShutterShopTheme {
        PaymentRatingBorder()
    }
}
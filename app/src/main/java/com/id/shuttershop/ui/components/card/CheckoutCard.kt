package com.id.shuttershop.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.domain.cart.CartModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun CheckoutCard(
    modifier: Modifier = Modifier,
    cartModel: CartModel,
) {
    Row(
        modifier = modifier
            .border(1.dp, DividerDefaults.color, RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .size(70.dp)
                .background(Color.Black)
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = cartModel.itemName,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = cartModel.itemVariantName, style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            Text(
                text = stringResource(
                    id = R.string.text_total_item,
                    cartModel.itemCount
                ), style = MaterialTheme.typography.labelSmall
            )
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = cartModel.getFormattedCurrency(),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
@Preview
internal fun CheckoutCardPreview() {
    ShutterShopTheme {
        CheckoutCard(
            cartModel = CartModel(
                itemId = "0",
                itemName = "Perfect Camera",
                itemVariantName = "16GB, 1TB SSD",
                itemStock = 1,
                itemCount = 1
            )
        )
    }
}
package com.id.shuttershop.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.domain.cart.CartModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.button.PrimaryIconButton
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun CartCard(
    modifier: Modifier = Modifier,
    cartModel: CartModel,
    isSelected: Boolean = false,
    onCheckClick: (Boolean) -> Unit,
    onItemAdd: () -> Unit = {},
    onItemMinus: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = isSelected, onCheckedChange = onCheckClick)
        Row(
            modifier = Modifier
                .border(1.dp, DividerDefaults.color, RoundedCornerShape(10.dp))
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(90.dp)
                    .background(Color.Black)
            )
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = cartModel.itemName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(text = cartModel.itemDesc, style = MaterialTheme.typography.bodySmall)
                if (cartModel.itemStock >= 5) {
                    Text(
                        text = stringResource(id = R.string.text_stock, cartModel.itemStock),
                        style = MaterialTheme.typography.labelMedium
                    )
                } else {
                    Text(
                        text = stringResource(
                            id = R.string.text_stock_remaining,
                            cartModel.itemStock
                        ), style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.error
                        )
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${cartModel.itemPrice * cartModel.itemStock}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    PrimaryIconButton(icon = Icons.Default.Delete)
                    ItemCountSection(
                        modifier = Modifier.padding(start = 5.dp),
                        itemCount = cartModel.itemCount,
                        onItemAdd = onItemAdd,
                        onItemMinus = onItemMinus
                    )
                }
            }
        }
    }
}

@Composable
internal fun ItemCountSection(
    modifier: Modifier = Modifier,
    itemCount: Int = 0,
    onItemAdd: () -> Unit = {},
    onItemMinus: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .border(2.dp, DividerDefaults.color, CircleShape)
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PrimaryIconButton(
            icon = ImageVector.vectorResource(id = R.drawable.baseline_remove_24),
            color = MaterialTheme.colorScheme.error,
            iconSize = 30.dp,
            onClick = onItemMinus
        )
        Text(
            text = itemCount.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
        )
        PrimaryIconButton(
            icon = Icons.Default.Add, iconSize = 30.dp,
            onClick = onItemAdd
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun CartCardPreview() {
    ShutterShopTheme {
        CartCard(
            onCheckClick = {}, cartModel = CartModel(
                itemId = 0,
                itemName = "Perfect Camera",
                itemDesc = "16GB, 1TB SSD",
                itemStock = 4,
                itemCount = 0
            )
        )
    }
}
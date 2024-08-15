package com.id.shuttershop.ui.components.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.id.domain.payment.PaymentModel
import com.id.domain.payment.PaymentType
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
fun PaymentCard(
    modifier: Modifier = Modifier,
    paymentModel: PaymentModel?,
    onPaymentClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .clickable(onClick = onPaymentClick)
            .padding(vertical = 18.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (paymentModel != null) {
            AsyncImage(
                modifier = Modifier
                    .size(30.dp),
                model = paymentModel.paymentImageUrl,
                contentDescription = paymentModel.paymentName,
                contentScale = ContentScale.Fit
            )
            Text(text = paymentModel.paymentName, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            PrimaryIconButton(icon = Icons.AutoMirrored.Default.ArrowForward)
        } else {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_payments_24),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = "Choose Payment Method", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            PrimaryIconButton(icon = Icons.AutoMirrored.Default.ArrowForward, enabled = false)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun PaymentCardPreview() {
    ShutterShopTheme {
        PaymentCard(
            paymentModel = PaymentModel(
                idPayment = 4545,
                paymentName = "Lesley Harrison",
                paymentImageUrl = "https://search.yahoo.com/search?p=parturient",
                paymentType = PaymentType.INSTANT_PAYMENT
            )
        )
    }
}
package com.id.shuttershop.ui.components.card.transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.PrimaryTextField
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun PaymentRatingCard(
    modifier: Modifier = Modifier,
    reviewValue: String,
    ratingValue: Int,
    onReviewChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit,
) {
    var ratingNumber by remember { mutableStateOf(0) }
    val onClick: (Int) -> Unit = {
        ratingNumber = if (it == ratingNumber) {
            -1
        } else {
            it
        }
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    PaymentRatingBorder {
        Column(
            modifier = modifier
                .padding(16.dp)
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Payment Successful",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = stringResource(R.string.text_transaction_rate),
                style = MaterialTheme.typography.titleMedium
            )
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                ((1..5).toList()).forEach { value ->
                    val imageVector = if (value <= ratingValue) {
                        Icons.Default.Star
                    } else {
                        ImageVector.vectorResource(id = R.drawable.baseline_star_border_24)
                    }
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = {
                                    onRatingChange(value)
                                }
                            ),
                        imageVector = imageVector,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            PrimaryTextField(
                title = "Transaction Review",
                value = reviewValue,
                onTextChange = onReviewChange
            )

        }
    }
}

@Composable
@Preview
internal fun PaymentStatusCardPreview() {
    ShutterShopTheme {
        PaymentRatingCard(
            reviewValue = "ridiculus",
            ratingValue = 4,
            onReviewChange = {},
            onRatingChange = {}
        )
    }
}
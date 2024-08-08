package com.id.shuttershop.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.domain.rating.RatingModel
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun RatingReviewCard(
    modifier: Modifier = Modifier,
    ratingModel: RatingModel
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Black)
                    .size(60.dp)
            )
            Column(
                modifier = Modifier.padding(start = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(text = ratingModel.userName, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    (1..ratingModel.ratingProduct).forEach { _ ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }
        Text(modifier = Modifier.padding(top = 10.dp), text = ratingModel.ratingDescription, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun RatingReviewCardPreview() {
    ShutterShopTheme {
        RatingReviewCard(ratingModel = RatingModel.emptyData)
    }
}
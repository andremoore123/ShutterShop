package com.id.shuttershop.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun NotificationCard(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Black)
                    .size(50.dp)
            )
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(text = "Info", style = MaterialTheme.typography.bodyMedium)
                Text(text = title, style = MaterialTheme.typography.titleMedium)
            }
        }
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = subTitle,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun NotificationCardPreview() {
    ShutterShopTheme {
        NotificationCard(
            title = "tempus",
            subTitle = "Transaksi anda sedang di proses oleh penjual, mohon ditunggu untuk update selanjutnya di aplikasi. Sambil menunggu, anda bisa cari barang lain terlebih dahulu"
        )
    }
}
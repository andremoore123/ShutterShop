package com.id.shuttershop.ui.components.card.wishlist;import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import com.id.domain.wishlist.WishlistModel
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Composable
fun WishlistRowCard(
    modifier: Modifier = Modifier,
    data: WishlistModel
) {

}

@Composable
@Preview
internal fun ShowWishlistRowCardPreview(){
    ShutterShopTheme{
        val data = WishlistModel.dummyData
        WishlistRowCard(data = data)
    }
}

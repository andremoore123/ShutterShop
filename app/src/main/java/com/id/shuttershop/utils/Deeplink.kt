package com.id.shuttershop.utils

import android.content.Context
import android.content.Intent

/**
 * Created by: andre.
 * Date: 16/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
object Deeplink {
    const val LINK_URL = "https://mymarket.phincon.site"
    fun shareProductDetail(context: Context, productName: String, productId: String) {
        val productTitle = "Hai, Check $productName Now!"
        val shareIntent: Intent = Intent().apply {
            val productLink = "$LINK_URL/$productId"
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, productLink)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(shareIntent, productTitle))
    }
}
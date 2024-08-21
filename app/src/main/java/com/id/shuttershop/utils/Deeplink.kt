package com.id.shuttershop.utils

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.id.shuttershop.MainActivity

/**
 * Created by: andre.
 * Date: 16/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
object Deeplink {
    const val LINK_URL = "https://mymarket.phincon.site"
    const val NOTIFICATION_URL = "https://notifcation.com"
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

    fun pendingIntentTransaction(context: Context): PendingIntent? {
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            NOTIFICATION_URL.toUri(),
            context,
            MainActivity::class.java
        )
        val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }

        return deepLinkPendingIntent
    }
}
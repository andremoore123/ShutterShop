package com.id.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.id.data.wishlist.source.WishlistDao
import com.id.data.wishlist.source.WishlistEntity

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Database(entities = [WishlistEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao

    companion object {
        fun buildDatabase(context: Context): AppDatabase = Room.databaseBuilder(
            context, AppDatabase::class.java, "shutter_shop"
        ).build()
    }
}
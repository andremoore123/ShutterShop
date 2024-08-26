package com.id.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.id.data.BuildConfig
import com.id.data.cart.CartDao
import com.id.data.cart.CartEntity
import com.id.data.wishlist.source.WishlistDao
import com.id.data.wishlist.source.WishlistEntity
import net.sqlcipher.database.SQLiteDatabase

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Database(entities = [WishlistEntity::class, CartEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
    abstract fun cartDao(): CartDao

    companion object {
        fun buildDatabase(context: Context): AppDatabase {
            val passphrase: ByteArray =
                SQLiteDatabase.getBytes(BuildConfig.SQL_CHIPPER_PASS.toCharArray())
            val factory = SQLCipherHelperFactory(passphrase)

            return Room.databaseBuilder(
                context, AppDatabase::class.java, "shutter_shop"
            )
                .openHelperFactory(factory)
                .build()
        }
    }
}
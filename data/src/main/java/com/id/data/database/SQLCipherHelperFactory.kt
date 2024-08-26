package com.id.data.database

import androidx.sqlite.db.SupportSQLiteOpenHelper
import net.sqlcipher.database.SupportFactory

/**
 * Created by: andre.
 * Date: 26/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class SQLCipherHelperFactory(passphrase: ByteArray) : SupportSQLiteOpenHelper.Factory {
    private val factory: SupportFactory = SupportFactory(passphrase)
    override fun create(configuration: SupportSQLiteOpenHelper.Configuration): SupportSQLiteOpenHelper {
        return factory.create(configuration)
    }
}

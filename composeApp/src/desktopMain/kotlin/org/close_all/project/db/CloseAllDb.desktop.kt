package org.close_all.project.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

actual fun getDb(): CloseAllDatabase {
    return getDatabaseBuilder().build()
}


fun getDatabaseBuilder(): RoomDatabase.Builder<CloseAllDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "closeAll.db")
    return Room.databaseBuilder<CloseAllDatabase>(
        name = dbFile.absolutePath,
    ).setDriver(BundledSQLiteDriver())
}
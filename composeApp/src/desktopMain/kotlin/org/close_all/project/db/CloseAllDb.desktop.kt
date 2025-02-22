package org.close_all.project.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import org.close_all.project.AppData
import java.io.File

actual fun getDb(): CloseAllDatabase {
    return getDatabaseBuilder().build()
}


fun getDatabaseBuilder(): RoomDatabase.Builder<CloseAllDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "closeAll.db")
    return Room.databaseBuilder<CloseAllDatabase>(
        name = dbFile.absolutePath,
    ).setDriver(BundledSQLiteDriver())
        .addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SQLiteConnection) {

                    // this app is hidden by default
                    db.execSQL(
                        "INSERT  INTO HiddenApp (name)  VALUES ('${AppData.getAppName()}');"
                    )
                }
            }
        )
}
package org.close_all.project.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [HiddenApp::class],
    version = 1
)
abstract class CloseAllDatabase : RoomDatabase() {
    abstract fun hiddenAppDao(): HiddenAppDao
}

expect fun getDb(): CloseAllDatabase
package org.close_all.project.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface HiddenAppDao {


    @Query("Delete  FROM HiddenApp where name=:appName")
    suspend fun delete(appName: String)

    @Query("SELECT * FROM HiddenApp")
    suspend fun _getAllHiddenApps(): List<HiddenApp>


    @Query("Delete  FROM HiddenApp")
    suspend fun _deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hiddenApps: List<HiddenApp>)

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insert(hiddenApp: HiddenApp)


    suspend fun getAllHiddenApps(): List<String> {
        return _getAllHiddenApps().map { it.name }
    }

    suspend fun insertHiddenApps(hiddenApps: List<String>) {
        insertAll(hiddenApps.map { HiddenApp(name = it) })
    }


}
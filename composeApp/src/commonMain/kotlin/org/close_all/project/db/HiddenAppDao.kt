package org.close_all.project.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface HiddenAppDao {

    @Upsert
    suspend fun upsert(person: HiddenApp)

    @Query("Delete  FROM HiddenApp where name=:appName")
    suspend fun delete(appName: String)

    @Query("SELECT * FROM HiddenApp")
    suspend fun _getAllHiddenApps(): List<HiddenApp>


    @Query("Delete  FROM HiddenApp")
    suspend fun _deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(persons: List<HiddenApp>)


    suspend fun getAllHiddenApps(): List<String> {
        return _getAllHiddenApps().map { it.name }
    }

    suspend fun insertHiddenApps(hiddenApps: List<String>) {
        insertAll(hiddenApps.map { HiddenApp(name = it) })
    }


}
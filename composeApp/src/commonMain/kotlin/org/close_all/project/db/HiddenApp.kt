package org.close_all.project.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class HiddenApp(
    @PrimaryKey(autoGenerate = false) val name: String,
)

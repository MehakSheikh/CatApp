package com.example.catapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CatDataEntity::class],
    version = 3,
    exportSchema = false,

    )
abstract class CatDatabase:RoomDatabase() {
    abstract val catDao: CatDAO
}
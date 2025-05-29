package com.challenge.shopping.fruits.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.challenge.shopping.fruits.common.data.AppConstants.FRUITS_DATABASE_NAME
import com.challenge.shopping.fruits.data.model.FruitEntity

@Database(
    entities = [FruitEntity::class],
    version = 1
)

@TypeConverters(ImageConverter::class)
internal abstract class FruitsLocalDatabase: RoomDatabase() {
    abstract fun fruitsDao(): FruitsLocalDataSource

    companion object {
        const val DB_NAME = FRUITS_DATABASE_NAME
    }
}
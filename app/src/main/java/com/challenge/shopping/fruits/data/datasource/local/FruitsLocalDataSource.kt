package com.challenge.shopping.fruits.data.datasource.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.challenge.shopping.fruits.data.model.FruitEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface FruitsLocalDataSource {

    @Query("SELECT * FROM FruitEntity")
    fun getAllFruits(): Flow<List<FruitEntity>>

    @Upsert
    suspend fun upsertFruit(fruitEntity: FruitEntity)

    @Query("DELETE FROM FruitEntity WHERE id = :id")
    suspend fun deleteFruitById(id: Int)
}
package com.challenge.shopping.fruits.data.datasource.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.challenge.shopping.fruits.data.model.FruitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitsLocalDataSource {

    @Query("SELECT * FROM FruitEntity")
    fun getFruitsOnCart(): Flow<List<FruitEntity>>

    @Query("SELECT * FROM FruitEntity WHERE id = :id")
    suspend fun getFruitOnCart(id: String): FruitEntity?

    @Upsert
    suspend fun upsertFruitOnCart(fruitEntity: FruitEntity)

    @Query("DELETE FROM FruitEntity WHERE id = :id")
    suspend fun deleteFruitFromCart(id: Int)
}
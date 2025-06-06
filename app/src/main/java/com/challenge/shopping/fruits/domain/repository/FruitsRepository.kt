package com.challenge.shopping.fruits.domain.repository

import androidx.compose.ui.graphics.ImageBitmap
import com.challenge.shopping.fruits.domain.model.Fruit
import com.challenge.shopping.fruits.common.domain.Result
import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.EmptyResult
import kotlinx.coroutines.flow.Flow

internal interface FruitsRepository {
    // Remote
    suspend fun getAllFruits(): Result<List<Fruit>, DataError>
    suspend fun getFruitImageByName(fruitName: String): Result<ImageBitmap, DataError>

    // Local
    fun getAllFruitsOnCart(): Flow<List<Fruit>>
    suspend fun addFruitOnCart(fruit: Fruit): EmptyResult<DataError.Local>
    suspend fun deleteFruitFromCartById(id: Int): EmptyResult<DataError.Local>
    fun isFruitOnCart(id: Int): Flow<Boolean>
}
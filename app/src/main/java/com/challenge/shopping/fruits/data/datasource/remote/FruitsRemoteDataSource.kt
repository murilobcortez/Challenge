package com.challenge.shopping.fruits.data.datasource.remote

import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.Result
import com.challenge.shopping.fruits.data.model.FruitResponse

interface FruitsRemoteDataSource {
    suspend fun getAllFruits(): Result<List<FruitResponse>, DataError.Remote>
    suspend fun getFruitImageByName(fruitName: String): Result<ByteArray, DataError>
}
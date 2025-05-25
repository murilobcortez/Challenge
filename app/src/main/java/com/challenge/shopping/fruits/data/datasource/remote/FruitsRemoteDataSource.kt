package com.challenge.shopping.fruits.data.datasource.remote

import androidx.compose.ui.graphics.ImageBitmap
import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.Result
import com.challenge.shopping.fruits.data.model.FruitResponse

interface FruitsRemoteDataSource {
    suspend fun getAllFruits(): Result<List<FruitResponse>, DataError.Remote>
    suspend fun getFruitAiGeneratedImage(fruitName: String): Result<ImageBitmap, DataError>
}
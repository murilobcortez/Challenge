package com.challenge.shopping.fruits.data.datasource.remote

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.challenge.shopping.fruits.common.data.AppConstants.AI_MODEL_NAME
import com.challenge.shopping.fruits.common.data.AppConstants.API_TOKEN
import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.Result
import com.challenge.shopping.fruits.data.datasource.remote.service.FruitAiImageGenerationApiService
import com.challenge.shopping.fruits.data.datasource.remote.service.FruitsApiService
import com.challenge.shopping.fruits.data.model.FruitAiImageGenerationRequest
import com.challenge.shopping.fruits.data.model.FruitResponse

class FruitsRemoteDataSourceImpl(
    private val fruitsApiService: FruitsApiService,
    private val aiImageApiService: FruitAiImageGenerationApiService
): FruitsRemoteDataSource {

    override suspend fun getAllFruits(): Result<List<FruitResponse>, DataError.Remote> {
        return try {
            val response = fruitsApiService.getAllFruits()
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error(DataError.Remote.SERVER)
            }
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun getFruitAiGeneratedImage(fruitName: String): Result<ImageBitmap, DataError.Remote> {
        val prompt = "A $fruitName on a basket, vibrant colors"
        val requestBody = FruitAiImageGenerationRequest(
            prompt = prompt,
            model = AI_MODEL_NAME
        )

        return try {
            val response = aiImageApiService.getFruitAiGeneratedImage(
                authorization = "Bearer $API_TOKEN",
                request = requestBody
            )
            if (response.isSuccessful) {
                val bytes = response.body()?.bytes()
                if (bytes != null) {
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    val imageBitmap = bitmap?.asImageBitmap()
                    if (imageBitmap != null) {
                        Result.Success(imageBitmap)
                    } else {
                        Result.Error(DataError.Remote.SERIALIZATION)
                    }
                } else {
                    Result.Success(ImageBitmap(1,1))
                }
            } else {
                Result.Error(DataError.Remote.SERVER)
            }
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }
}
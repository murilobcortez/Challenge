package com.challenge.shopping.fruits.data.datasource.remote

import com.challenge.shopping.fruits.common.data.AppConstants.AI_MODEL_NAME
import com.challenge.shopping.fruits.common.data.AppConstants.API_TOKEN
import com.challenge.shopping.fruits.common.data.safeCall
import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.Result
import com.challenge.shopping.fruits.data.datasource.remote.service.FruitImageApiService
import com.challenge.shopping.fruits.data.datasource.remote.service.FruitsApiService
import com.challenge.shopping.fruits.data.model.FruitGenerateImageRequest
import com.challenge.shopping.fruits.data.model.FruitResponse

internal class FruitsRemoteDataSourceImpl(
    private val fruitsApiService: FruitsApiService,
    private val fruitImageApiService: FruitImageApiService
): FruitsRemoteDataSource {

    override suspend fun getAllFruits(): Result<List<FruitResponse>, DataError.Remote> {
        return safeCall {
            fruitsApiService.getAllFruits()
        }
    }

    override suspend fun getFruitImageByName(fruitName: String): Result<ByteArray, DataError.Remote> {
        val prompt = "A $fruitName on a basket, vibrant colors"
        val requestBody = FruitGenerateImageRequest(
            prompt = prompt,
            model = AI_MODEL_NAME
        )

        return safeCall {
            val response = fruitImageApiService.generateFruitImage(
                authorization = "Bearer $API_TOKEN",
                request = requestBody
            )
            if (response.isSuccessful) {
                val bytes = response.body()?.bytes()
                retrofit2.Response.success(bytes)
            } else {
                retrofit2.Response.error(response.errorBody()!!, response.raw())
            }
        }
    }
}
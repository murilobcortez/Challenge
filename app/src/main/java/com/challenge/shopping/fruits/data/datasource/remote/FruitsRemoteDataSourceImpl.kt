package com.challenge.shopping.fruits.data.datasource.remote

import com.challenge.shopping.fruits.common.data.AppConstants.AI_MODEL_NAME
import com.challenge.shopping.fruits.common.data.AppConstants.API_TOKEN
import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.Result
import com.challenge.shopping.fruits.data.datasource.remote.service.FruitImageApiService
import com.challenge.shopping.fruits.data.datasource.remote.service.FruitsApiService
import com.challenge.shopping.fruits.data.model.FruitGenerateImageRequest
import com.challenge.shopping.fruits.data.model.FruitResponse
import retrofit2.HttpException
import java.io.IOException

class FruitsRemoteDataSourceImpl(
    private val fruitsApiService: FruitsApiService,
    private val fruitImageApiService: FruitImageApiService
): FruitsRemoteDataSource {

    override suspend fun getAllFruits(): Result<List<FruitResponse>, DataError.Remote> {
        return try {
            val response = fruitsApiService.getAllFruits()

            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error(DataError.Remote.SERVER)
            }

        } catch (e: IOException) {
            Result.Error(DataError.Remote.NETWORK)
        } catch (e: HttpException) {
            Result.Error(DataError.Remote.SERVER)
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun generateFruitImageByName(fruitName: String): Result<ByteArray, DataError.Remote> {
        val prompt = "A $fruitName on a basket, vibrant colors"
        val requestBody = FruitGenerateImageRequest(
            prompt = prompt,
            model = AI_MODEL_NAME
        )

        return try {
            val response = fruitImageApiService.generateFruitImage(
                authorization = "Bearer $API_TOKEN",
                request = requestBody
            )

            if (response.isSuccessful) {
                val bytes = response.body()?.bytes()
                if (bytes != null) {
                    Result.Success(bytes)
                } else {
                    Result.Error(DataError.Remote.SERIALIZATION)
                }
            } else {
                Result.Error(DataError.Remote.SERVER)
            }
        } catch (e: IOException) {
            Result.Error(DataError.Remote.NETWORK)
        } catch (e: HttpException) {
            Result.Error(DataError.Remote.SERVER)
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }
}
package com.challenge.shopping.fruits.data.datasource.remote.service

import com.challenge.shopping.fruits.common.data.AppConstants.ACCOUNT_ID
import com.challenge.shopping.fruits.common.data.AppConstants.AI_MODEL_NAME
import com.challenge.shopping.fruits.data.model.FruitGenerateImageRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

const val FRUIT_IMAGE_ENDPOINT = "accounts/$ACCOUNT_ID/ai/run/$AI_MODEL_NAME"


interface FruitImageApiService {
    @POST(FRUIT_IMAGE_ENDPOINT)
    @Headers("Content-Type: application/json")
    suspend fun generateFruitImage(
        @Header("Authorization") authorization: String,
        @Body request: FruitGenerateImageRequest
    ): Response<ResponseBody>
}
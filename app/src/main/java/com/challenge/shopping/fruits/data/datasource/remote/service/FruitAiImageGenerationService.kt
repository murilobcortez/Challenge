package com.challenge.shopping.fruits.data.datasource.remote.service

import com.challenge.shopping.fruits.data.model.FruitAiImageGenerationRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

private const val ACCOUNT_ID = "be827858d7c4b7576e7bfcde3e3886fa"
private const val AI_MODEL_NAME = "@cf/bytedance/stable-diffusion-xl-lightning"
private const val AI_IMAGE_GENERATION_ENDPOINT = "accounts/$ACCOUNT_ID/ai/run/$AI_MODEL_NAME"

interface FruitAiImageGenerationApiService {
    @POST(AI_IMAGE_GENERATION_ENDPOINT)
    @Headers("Content-Type: application/json")
    suspend fun getFruitAiGeneratedImage(
        @Header("Authorization") authorization: String,
        @Body request: FruitAiImageGenerationRequest
    ): Response<ResponseBody>
}
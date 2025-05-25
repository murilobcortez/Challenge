package com.challenge.shopping.fruits.data.repository.mock

import com.challenge.shopping.fruits.data.datasource.remote.service.FruitsApiService
import com.challenge.shopping.fruits.data.model.FruitResponse
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.io.File

class FruitsApiServiceMock(val jsonResponsePathFile: String) : FruitsApiService {
    override suspend fun getAllFruits(): Response<List<FruitResponse>> {
        val json = File(jsonResponsePathFile).readText()
        val result = Json { ignoreUnknownKeys = true }.decodeFromString<List<FruitResponse>>(json)
        return Response.success(result)
    }
}
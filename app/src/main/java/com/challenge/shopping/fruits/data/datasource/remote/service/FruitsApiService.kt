package com.challenge.shopping.fruits.data.datasource.remote.service

import com.challenge.shopping.fruits.data.model.FruitResponse
import retrofit2.Response
import retrofit2.http.GET

private const val GET_ALL_FRUITS_ENDPOINT = "fruit/all"

interface FruitsApiService {
    @GET(GET_ALL_FRUITS_ENDPOINT)
    suspend fun getAllFruits(): Response<List<FruitResponse>>
}
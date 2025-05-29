package com.challenge.shopping.fruits.domain.usecase

import com.challenge.shopping.fruits.domain.model.Fruit
import com.challenge.shopping.fruits.domain.repository.FruitsRepository
import kotlinx.coroutines.flow.Flow

internal class GetFruitsOnCartUseCase(
    private val repository: FruitsRepository
){
    operator fun invoke(): Flow<List<Fruit>>{
        return repository.getAllFruitsOnCart()
    }
}
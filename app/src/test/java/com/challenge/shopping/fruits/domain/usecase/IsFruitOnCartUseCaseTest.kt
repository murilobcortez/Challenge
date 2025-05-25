package com.challenge.shopping.fruits.domain.usecase

import com.challenge.shopping.fruits.domain.repository.FruitsRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import app.cash.turbine.test

class IsFruitOnCartUseCaseTest {

    private lateinit var repository: FruitsRepository
    private lateinit var useCase: IsFruitOnCartUseCase

    @BeforeEach
    fun setUp() {
        repository = mock()
        useCase = IsFruitOnCartUseCase(repository)
    }

    @Test
    fun `should emit true when fruit is on cart`() = runTest {
        // Given
        val fruitId = 1
        whenever(repository.isFruitOnCart(fruitId)).thenReturn(flowOf(true))

        // When & Then
        useCase(fruitId).test {
            val emitted = awaitItem()
            assert(emitted == true)
            awaitComplete()
        }
    }

    @Test
    fun `should emit false when fruit is not on cart`() = runTest {
        // Given
        val fruitId = 2
        whenever(repository.isFruitOnCart(fruitId)).thenReturn(flowOf(false))

        // When & Then
        useCase(fruitId).test {
            val emitted = awaitItem()
            assert(emitted == false)
            awaitComplete()
        }
    }
}

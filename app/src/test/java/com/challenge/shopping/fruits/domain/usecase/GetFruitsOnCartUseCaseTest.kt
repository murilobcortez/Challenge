package com.challenge.shopping.fruits.domain.usecase

import app.cash.turbine.test
import com.challenge.shopping.fruits.domain.stub.TestStub
import com.challenge.shopping.fruits.domain.repository.FruitsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetFruitsOnCartUseCaseTest {

    private lateinit var repository: FruitsRepository
    private lateinit var useCase: GetFruitsOnCartUseCase

    @BeforeEach
    fun setUp() {
        repository = mock()
        useCase = GetFruitsOnCartUseCase(repository)
    }

    @Test
    fun `should emit list of fruits from repository`() = runTest {
        // Given
        val fruits = TestStub.generateFruitsList()
        whenever(repository.getAllFruitsOnCart()).thenReturn(flowOf(fruits))

        // When & Then
        useCase().test {
            val emitted = awaitItem()
            assert(emitted == fruits)
            awaitComplete()
        }
    }

    @Test
    fun `should emit error when repository flow throws exception`() = runTest {
        // Given
        val exception = RuntimeException("Test error")
        whenever(repository.getAllFruitsOnCart()).thenReturn(flow {
            throw exception
        })

        // When & Then
        useCase().test {
            val error = awaitError()
            assert(error == exception)
        }
    }
}
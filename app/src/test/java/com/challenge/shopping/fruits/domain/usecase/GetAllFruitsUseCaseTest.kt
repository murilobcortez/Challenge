package com.challenge.shopping.fruits.domain.usecase

import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.Result
import com.challenge.shopping.fruits.domain.stub.TestStub
import com.challenge.shopping.fruits.domain.repository.FruitsRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAllFruitsUseCaseTest {

    private lateinit var repository: FruitsRepository
    private lateinit var useCase: GetAllFruitsUseCase

    @BeforeEach
    fun setUp() {
        repository = mock()
        useCase = GetAllFruitsUseCase(repository)
    }

    @Test
    fun `should return list of fruits when repository returns success`() = runBlocking {
        // Given
        val fruits = TestStub.generateFruitsList()
        whenever(repository.getAllFruits()).thenReturn(Result.Success(fruits))

        // When
        val result = useCase.invoke()

        // Then
        assertEquals(Result.Success(fruits), result)
    }

    @Test
    fun `should return error when repository returns request timeout`() = runBlocking {
        // Given
        val error = DataError.Remote.REQUEST_TIMEOUT
        whenever(repository.getAllFruits()).thenReturn(Result.Error(error))

        // When
        val result = useCase.invoke()

        // Then
        assertEquals(Result.Error(error), result)
    }
}
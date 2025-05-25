import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.common.domain.Result
import com.challenge.shopping.fruits.data.datasource.local.FruitsLocalDataSource
import com.challenge.shopping.fruits.data.datasource.remote.FruitsRemoteDataSourceImpl
import com.challenge.shopping.fruits.data.datasource.remote.service.FruitAiImageGenerationApiService
import com.challenge.shopping.fruits.data.datasource.remote.service.FruitsApiService
import com.challenge.shopping.fruits.data.repository.FruitsRepositoryImpl
import com.challenge.shopping.fruits.data.repository.mock.FruitsApiServiceMock
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class FruitsRepositoryIntegrationTest {

    private fun createRepository(
        fruitsApiService: FruitsApiService = mock(),
        fruitAiImageGenerationApiService: FruitAiImageGenerationApiService = mock(),
        localDataSource: FruitsLocalDataSource = mock()
    ) = FruitsRepositoryImpl(
        localDataSource, FruitsRemoteDataSourceImpl(
            fruitsApiService, fruitAiImageGenerationApiService
        )
    )

    @Test
    fun `should map fruits correctly from success mocked json file`() = runBlocking {
        val repository = createRepository(
            FruitsApiServiceMock("src/test/resources/fruits_mock_success_response.json")
        )

        val result = repository.getAllFruits()

        when (result) {
            is Result.Success -> assertAll(
                { assertEquals("Apple", result.data[0].name) },
                { assertEquals("Banana", result.data[1].name) },
                { assertEquals(2, result.data.size) }
            )
            else -> error("Expected result success but got $result")
        }
    }

    @Test
    fun `should return unknown error when API service throws exception`() = runBlocking {
        val mockedApiService = mock<FruitsApiService>()
        whenever(mockedApiService.getAllFruits()).thenThrow(RuntimeException())
        val repository = createRepository(mockedApiService)

        val result = repository.getAllFruits()

        when (result) {
            is Result.Error -> assertEquals(DataError.Remote.UNKNOWN, result.error)
            else -> error("Expected result unknown error but got $result")
        }
    }
}
import androidx.compose.ui.graphics.ImageBitmap
import com.challenge.shopping.fruits.common.domain.DataError
import com.challenge.shopping.fruits.domain.model.Fruit
import com.challenge.shopping.fruits.domain.usecase.GetAllFruitsUseCase
import com.challenge.shopping.fruits.domain.usecase.GetFruitImageUseCase
import com.challenge.shopping.fruits.domain.usecase.GetFruitsOnCartUseCase
import com.challenge.shopping.fruits.presentation.fruitslist.FruitsListViewModel
import com.challenge.shopping.fruits.common.domain.Result
import com.challenge.shopping.fruits.presentation.stub.TestStub
import com.challenge.shopping.fruits.presentation.fruitslist.FruitsListAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FruitsListViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getAllFruitsUseCase: GetAllFruitsUseCase
    private lateinit var getFruitsOnCartUseCase: GetFruitsOnCartUseCase
    private lateinit var getFruitImageUseCase: GetFruitImageUseCase

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getAllFruitsUseCase = mock()
        getFruitsOnCartUseCase = mock()
        getFruitImageUseCase = mock()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): FruitsListViewModel =
        FruitsListViewModel(
            getAllFruitsUseCase,
            getFruitsOnCartUseCase,
            getFruitImageUseCase,
            testDispatcher
        )

    @Test
    fun `init should set loading as true and state with empty fruits list`() = runTest {
        // Given
        whenever(getFruitsOnCartUseCase.invoke()).thenReturn(flowOf(emptyList()))
        whenever(getAllFruitsUseCase.invoke()).thenReturn(Result.Success(emptyList()))

        // When
        val viewModel = createViewModel()

        // Then
        val state = viewModel.state.value
        assertEquals(true, state.isLoading)
        assertEquals(emptyList<Fruit>(), state.fruits)
    }

    @Test
    fun `fetchFruits should set loading as false and state with fruits when success`() = runTest {
        // Given
        val mockImageBitmap = Mockito.mock(ImageBitmap::class.java)
        val fruits = TestStub.generateFruitsList(mockImageBitmap)
        whenever(getFruitsOnCartUseCase.invoke()).thenReturn(flowOf(emptyList()))
        whenever(getAllFruitsUseCase.invoke()).thenReturn(Result.Success(fruits))
        whenever(getFruitImageUseCase.invoke(any())).thenReturn(Result.Success(mockImageBitmap))
        val viewModel = createViewModel()

        // When
        viewModel.getFruits()
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(fruits, state.fruits)
        assertEquals(null, state.errorMessage)
    }

    @Test
    fun `fetchFruits should set loading as false state with errorMessage when error`() = runTest {
        // Given
        val error = DataError.Remote.REQUEST_TIMEOUT
        whenever(getFruitsOnCartUseCase.invoke()).thenReturn(flowOf(emptyList()))
        whenever(getAllFruitsUseCase.invoke()).thenReturn(Result.Error(error))
        val viewModel = createViewModel()

        // When
        viewModel.getFruits()
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(emptyList<Fruit>(), state.fruits)
        assert(state.errorMessage != null)
    }

    @Test
    fun `onAction OnTapSelected should update selectedTabIndex with correct tab`() = runTest {
        // Given
        whenever(getFruitsOnCartUseCase.invoke()).thenReturn(flowOf(emptyList()))
        whenever(getAllFruitsUseCase.invoke()).thenReturn(Result.Success(emptyList()))
        val viewModel = createViewModel()

        // When
        viewModel.onAction(FruitsListAction.OnTapSelected(2))

        // Then
        val state = viewModel.state.value
        assertEquals(2, state.selectedTabIndex)
    }
}
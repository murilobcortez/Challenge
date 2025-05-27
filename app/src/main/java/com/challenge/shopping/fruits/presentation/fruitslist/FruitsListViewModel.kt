package com.challenge.shopping.fruits.presentation.fruitslist

import androidx.compose.ui.graphics.ImageBitmap
import com.challenge.shopping.fruits.domain.model.Fruit
import com.challenge.shopping.fruits.domain.usecase.GetAllFruitsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.shopping.fruits.common.domain.onError
import com.challenge.shopping.fruits.common.domain.onSuccess
import com.challenge.shopping.fruits.common.presentation.toUiText
import com.challenge.shopping.fruits.domain.usecase.GetFruitImageUseCase
import com.challenge.shopping.fruits.domain.usecase.GetFruitsOnCartUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FruitsListViewModel(
    private val getAllFruitsUseCase: GetAllFruitsUseCase,
    private val getFruitsOnCartUseCase: GetFruitsOnCartUseCase,
    private val getFruitImageUseCase: GetFruitImageUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var cachedFruits = emptyList<Fruit>()
    private var observeFruitsAddedToCartJob: Job? = null

    private val _state = MutableStateFlow(FruitsListState())
    val state: StateFlow<FruitsListState> = _state

    init {
        fetchFruitsIfNeeded()
        observeFruitsAddedToCart()
    }

    private fun fetchFruitsIfNeeded() {
        if (cachedFruits.isEmpty()) {
            fetchFruits()
        }
    }

    fun onAction(action: FruitsListAction){
        when(action){
            is FruitsListAction.OnTapSelected -> {
                _state.update {
                    it.copy(
                        selectedTabIndex = action.index
                    )
                }
            }
            else -> Unit
        }
    }

    fun fetchFruits() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch(dispatcher) {
            getAllFruitsUseCase()
                .onSuccess { fruitsList ->
                    cachedFruits = fruitsList
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            fruits = fruitsList
                        )
                    }
                    fetchFruitsImages(fruitsList)
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.toUiText(),
                            fruits = emptyList()
                        )
                    }
                }
        }
    }

    fun fetchFruitsImages(fruitsList : List<Fruit>){
       fruitsList.forEach { fruit ->
            viewModelScope.launch(dispatcher) {
                getFruitImageUseCase(fruit.name)
                    .onSuccess { fruitImage ->
                        _state.update { currentState ->

                            val updatedFruits = currentState.fruits.map { currentFruit ->
                                if (currentFruit.name == fruit.name) {
                                    currentFruit.copy(image = fruitImage)
                                } else {
                                    currentFruit
                                }
                            }
                            cachedFruits = updatedFruits

                            currentState.copy(fruits = updatedFruits)
                        }
                    }
                    .onError {
                        _state.update { currentState ->
                            val updatedFruits = currentState.fruits.map { currentFruit ->
                                if (currentFruit.name == fruit.name) {
                                    currentFruit.copy(image = ImageBitmap(1, 1))
                                } else {
                                    currentFruit
                                }
                            }
                            cachedFruits = updatedFruits

                            currentState.copy(fruits = updatedFruits)
                        }
                    }
            }
        }
    }

    private fun observeFruitsAddedToCart(){
        observeFruitsAddedToCartJob?.cancel()

        observeFruitsAddedToCartJob = getFruitsOnCartUseCase()
            .onEach { fruitsAddedOnCart ->
                _state.update { it.copy(
                    fruitsAddedOnCart = fruitsAddedOnCart
                ) }
            }
            .launchIn(viewModelScope)
    }
}
package com.challenge.shopping.fruits.presentation.fruitslist

import androidx.compose.ui.graphics.ImageBitmap
import com.challenge.shopping.fruits.domain.model.Fruit
import com.challenge.shopping.fruits.domain.usecase.GetAllFruitsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.shopping.fruits.common.domain.onError
import com.challenge.shopping.fruits.common.domain.onSuccess
import com.challenge.shopping.fruits.common.presentation.toUiText
import com.challenge.shopping.fruits.common.utils.emptyImageBitmap
import com.challenge.shopping.fruits.domain.usecase.GetFruitImageUseCase
import com.challenge.shopping.fruits.domain.usecase.GetFruitsOnCartUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class FruitsListViewModel(
    private val getAllFruitsUseCase: GetAllFruitsUseCase,
    private val getFruitsOnCartUseCase: GetFruitsOnCartUseCase,
    private val getFruitImageUseCase: GetFruitImageUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var cachedFruits = emptyList<Fruit>()
    private var observeFruitsAddedToCartJob: Job? = null

    private val _state = MutableStateFlow(FruitsListState())
    val state: StateFlow<FruitsListState> = _state

    init {
        getFruitsIfNeeded()
        observeFruitsAddedToCart()
    }

    private fun getFruitsIfNeeded() {
        if (cachedFruits.isEmpty()) {
            getFruits()
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

    fun getFruits() {
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
                    getFruitsImages(fruitsList)
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

    fun getFruitsImages(fruitsList : List<Fruit>){
        fruitsList.forEach { fruit ->
            viewModelScope.launch(dispatcher) {
                getFruitImageUseCase(fruit.name)
                    .onSuccess { fruitImage ->
                        updateFruitImageOnStateAndCache(fruit, fruitImage)
                    }
                    .onError {
                        updateFruitImageOnStateAndCache(fruit)
                    }
            }
        }
    }

    private fun updateFruitImageOnStateAndCache(fruit: Fruit, fruitImage: ImageBitmap? = null){
        _state.update { currentState ->
            val updatedFruits = currentState.fruits.map { currentFruit ->
                if (currentFruit.name == fruit.name) {
                    fruitImage?.let {
                        currentFruit.copy(image = fruitImage)
                    } ?: currentFruit.copy(image = emptyImageBitmap())
                } else {
                    currentFruit
                }
            }
            cachedFruits = updatedFruits

            currentState.copy(fruits = updatedFruits)
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
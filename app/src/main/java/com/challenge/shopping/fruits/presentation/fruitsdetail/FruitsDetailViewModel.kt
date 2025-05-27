package com.challenge.shopping.fruits.presentation.fruitsdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.challenge.shopping.app.Route
import com.challenge.shopping.fruits.domain.usecase.AddFruitOnCartUseCase
import com.challenge.shopping.fruits.domain.usecase.DeleteFruitFromCartUseCase
import com.challenge.shopping.fruits.domain.usecase.IsFruitOnCartUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FruitsDetailViewModel(
    private val deleteFruitFromCartUseCase: DeleteFruitFromCartUseCase,
    private val addFruitOnCartUseCase: AddFruitOnCartUseCase,
    private val isFruitOnCartUseCase: IsFruitOnCartUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val fruitId = savedStateHandle.toRoute<Route.FruitsDetail>().id

    private val _state = MutableStateFlow(FruitsDetailState())
    val state: StateFlow<FruitsDetailState> = _state

    init {
        observeCartStatus()
    }

    fun onAction(action: FruitsDetailAction){
        when(action){
            is FruitsDetailAction.OnCartFruitsChange -> _state.update {
                it.copy(
                    fruit = action.fruit
                )
            }
            FruitsDetailAction.OnAddToCartClick -> {
                handleAddOrRemoveFromCart()
            }
            else -> Unit
        }
    }

    private fun handleAddOrRemoveFromCart() {
        viewModelScope.launch(dispatcher) {
            if (state.value.isAddedToCart) {
                deleteFruitFromCartUseCase(fruitId)
            } else {
                state.value.fruit?.let { fruit ->
                    addFruitOnCartUseCase(fruit)
                }
            }
        }
    }

    private fun observeCartStatus(){
        isFruitOnCartUseCase(fruitId)
            .onEach { isAddedToCart ->
                _state.update { it.copy(
                    isAddedToCart = isAddedToCart
                ) }
            }
            .launchIn(viewModelScope)
    }
}

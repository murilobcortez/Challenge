package com.challenge.shopping.fruits.presentation

import androidx.lifecycle.ViewModel
import com.challenge.shopping.fruits.domain.model.Fruit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class SelectedFruitSharedViewModel : ViewModel() {
    private val _selectedFruit = MutableStateFlow<Fruit?>(null)
    val selectedFruit = _selectedFruit.asStateFlow()

    fun onSelectFruit(fruit: Fruit?){
        _selectedFruit.value = fruit
    }
}
package com.challenge.shopping.fruits.presentation.fruitslist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.challenge.R
import com.challenge.shopping.fruits.domain.model.Fruit

@Composable
fun FruitsPage(
    isLoading: Boolean,
    errorMessage: String?,
    fruits: List<Fruit>,
    onFruitClick: (Fruit) -> Unit,
    listState: LazyListState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> Text(
                text = errorMessage,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error
            )
            fruits.isEmpty() -> Text(
                text = stringResource(R.string.fruit_list_screen_no_search_results),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error
            )
            else -> FruitList(
                fruits = fruits,
                onFruitClick = onFruitClick,
                modifier = Modifier.fillMaxSize(),
                scrollState = listState
            )
        }
    }
}
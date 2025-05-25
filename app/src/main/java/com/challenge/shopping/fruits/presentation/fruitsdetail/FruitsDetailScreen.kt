package com.challenge.shopping.fruits.presentation.fruitsdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.challenge.R
import com.challenge.shopping.fruits.presentation.fruitsdetail.components.AddToCartButton
import com.challenge.shopping.fruits.presentation.fruitsdetail.components.FruitDetailItem
import com.challenge.shopping.fruits.presentation.fruitsdetail.components.ImageBackground

@Composable
fun FruitsDetailScreenRoot(
    viewModel: FruitsDetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FruitsDetailScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is FruitsDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FruitsDetailScreen(
    state: FruitsDetailState,
    onAction: (FruitsDetailAction) -> Unit
){
    ImageBackground(
        image = state.fruit?.image,
        onBackClick = { onAction(FruitsDetailAction.OnBackClick) },
        modifier = Modifier.fillMaxSize()
    ){
        if(state.fruit != null){
            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 24.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.fruit.name,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Family
                    state.fruit.family.let { family ->
                        FruitDetailItem(
                            titleText = stringResource(R.string.fruit_detail_screen_fruit_family),
                            descriptionText = family
                        )
                    }
                    // Order
                    state.fruit.order.let { order ->
                        FruitDetailItem(
                            titleText = stringResource(R.string.fruit_detail_screen_fruit_order),
                            descriptionText = order
                        )
                    }
                    //Genus
                    state.fruit.genus.let { genus ->
                        FruitDetailItem(
                            titleText = stringResource(R.string.fruit_detail_screen_fruit_genus),
                            descriptionText = genus
                        )
                    }
                    //Price
                    state.fruit.price.let { price ->
                        FruitDetailItem(
                            titleText = stringResource(R.string.fruit_detail_screen_fruit_price),
                            descriptionText = price
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                AddToCartButton(
                    isAddedToCart = state.isAddedToCart,
                    onClick = { onAction(FruitsDetailAction.OnAddToCartClick) }
                )
            }
        } else {
            Text(
                text = stringResource(R.string.fruit_detail_screen_fruit_not_identified),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}
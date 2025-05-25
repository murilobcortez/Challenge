package com.challenge.shopping.fruits.presentation.fruitslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.challenge.R
import com.challenge.shopping.fruits.common.presentation.DesertWhite
import com.challenge.shopping.fruits.common.presentation.Red
import com.challenge.shopping.fruits.domain.model.Fruit
import com.challenge.shopping.fruits.presentation.fruitslist.components.CartPage
import com.challenge.shopping.fruits.presentation.fruitslist.components.FruitTabRow
import com.challenge.shopping.fruits.presentation.fruitslist.components.FruitsPage
import org.koin.compose.viewmodel.koinViewModel

enum class FruitsTab(val titleRes: Int) {
    Fruits(R.string.fruit_list_screen_tab_fruits),
    Cart(R.string.fruit_list_screen_tab_cart)
}

@Composable
fun FruitsListScreenRoot(
    viewModel: FruitsListViewModel = koinViewModel(),
    onFruitClick: (Fruit) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FruitListScreen(
        state = state,
        onAction = { action ->
            if (action is FruitsListAction.OnFruitClick) onFruitClick(action.fruit)
            viewModel.onAction(action)
        }
    )
}

@Composable
fun FruitListScreen(
    state: FruitsListState,
    onAction: (FruitsListAction) -> Unit,
) {
    val pagerState = rememberPagerState { FruitsTab.entries.size }
    val fruitsListState = rememberLazyListState()
    val fruitsAddedToCartListState = rememberLazyListState()

    LaunchedEffect(state.selectedTabIndex) {
        if (pagerState.currentPage != state.selectedTabIndex) {
            pagerState.animateScrollToPage(state.selectedTabIndex)
        }
    }
    LaunchedEffect(pagerState.currentPage) {
        if (state.selectedTabIndex != pagerState.currentPage) {
            onAction(FruitsListAction.OnTapSelected(pagerState.currentPage))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Red)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                FruitTabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    onTabSelected = { onAction(FruitsListAction.OnTapSelected(it)) }
                )
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { pageIndex ->
                    when (FruitsTab.entries[pageIndex]) {
                        FruitsTab.Fruits -> FruitsPage(
                            isLoading = state.isLoading,
                            errorMessage = state.errorMessage?.asString(),
                            fruits = state.fruits,
                            onFruitClick = { onAction(FruitsListAction.OnFruitClick(it)) },
                            listState = fruitsListState
                        )
                        FruitsTab.Cart -> CartPage(
                            fruitsInCart = state.fruitsAddedOnCart,
                            onFruitClick = { onAction(FruitsListAction.OnFruitClick(it)) },
                            listState = fruitsAddedToCartListState
                        )
                    }
                }
            }
        }
    }
}